package serialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GoogleApiSerialization {
	
	

	@Test
	public void SerializationExample() {
		AddLocation al = new AddLocation();
		al.setAccuracy(50);
		al.setName("Stan Marshal");
		al.setPhone_number("(+91)950059193");
		al.setAddress("Electronic City");
		List<String> type = new ArrayList<String>();
		type.add("shoe park");
		type.add("shop");
		al.setTypes(type);
		al.setWebsite("http://google.com");
		al.setLanguage("French-IN");
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		al.setLocation(location);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response res = given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123")
		.body(al)
		.when().post("/maps/api/place/add/json")
		.then().extract().response();
		
		System.out.println(res.statusCode());
		System.out.println(res.asString());
	}
}
