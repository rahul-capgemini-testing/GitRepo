package rahulShettyAcademy;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RestAssured.useRelaxedHTTPSValidation();
		String response = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Rahul Azazel\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"https://rahulshettyacademy.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "}").when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("status", equalTo("OK"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		JsonPath jp = new JsonPath(response);
		String place_id = jp.getString("place_id");
		//System.out.println(place_id);
		System.out.println("----------------------------");
		
		String putRespond = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\"Bengaluru, Karnataka, India\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
		System.out.println(putRespond);
		System.out.println("----------------------------");
		
		String getResponse = given().log().all().queryParam("key","qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		//System.out.println(getResponse);
		JsonPath jps = new JsonPath(getResponse);
		String name = jps.getString("name");
		System.out.println(name);
	}
}
