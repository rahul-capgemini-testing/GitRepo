package rahulShettyAcademy;

import static org.hamcrest.Matchers.lessThan;

import java.util.concurrent.TimeUnit;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.ResponseSpecification;

public class CommonCode {
	
	public static String jsonCourses() {
		return "{\r\n"
				+ "\r\n"
				+ "\"dashboard\": {\r\n"
				+ "\r\n"
				+ "\"purchaseAmount\": 910,\r\n"
				+ "\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "\"courses\": [\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Selenium Python\",\r\n"
				+ "\r\n"
				+ "\"price\": 50,\r\n"
				+ "\r\n"
				+ "\"copies\": 6\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Cypress\",\r\n"
				+ "\r\n"
				+ "\"price\": 40,\r\n"
				+ "\r\n"
				+ "\"copies\": 4\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"RPA\",\r\n"
				+ "\r\n"
				+ "\"price\": 45,\r\n"
				+ "\r\n"
				+ "\"copies\": 10\r\n"
				+ "\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "]\r\n"
				+ "\r\n"
				+ "}";
	}
	
	public static ResponseSpecification responseStatusCodeTime(int statusCode, long responseTime) {
		return new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(lessThan(800L), TimeUnit.MILLISECONDS)
				.build();
	}
	
	public static String addBook(String bookName, String isbn, String aisle, String author) {
		return "{\r\n"
				+ "\r\n"
				+ "\"name\":\""+bookName+"\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n"
				+ "\"author\":\""+author+"\"\r\n"
				+ "}\r\n"
				+ "";
	}
	
	public static String deleteBook(String ID) {
		return "{\r\n"
				+ " \r\n"
				+ "\"ID\" : \""+ID+"\"\r\n"
				+ " \r\n"
				+ "} \r\n"
				+ "";
	}
	
	public static JsonPath rawToJson(String response) {
		return new JsonPath(response);
	}
}
