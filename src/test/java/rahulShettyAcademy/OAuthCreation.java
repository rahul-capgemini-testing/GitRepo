package rahulShettyAcademy;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthCreation {
	@Test
	public void oauthCreation() throws IOException {
		String [] WebAutomationCourseTitles= {"Selenium Webdriver Java","Cypress","Protractor"};
		String accessToken;
		Response oathResponse = given().relaxedHTTPSValidation().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParams("grant_type","client_credentials")
		.formParams("scope","trust")
		.when()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().extract().response();
		System.out.println("---------------");
		JsonPath jp = new JsonPath(oathResponse.asString());
		accessToken = jp.getString("access_token");
		System.out.println("Status Code is "+oathResponse.getStatusCode());
		System.out.println("Access Token = "+accessToken);
		
		//Get the course details
		GetCourse courseResponse = given().relaxedHTTPSValidation().queryParams("access_token",accessToken)
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.as(GetCourse.class);
		System.out.println("---------------------");
		System.out.println(courseResponse.getLinkedIn());
		
		List<Api> apiList = courseResponse.getCourses().getApi();
		for(Api api : apiList) {
			if(api.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(api.getCourseTitle()+" : "+api.getPrice());
			}
		}
		int count = 1;
		ArrayList <String> al = new ArrayList<String>();
		List<WebAutomation> webAutomationList = courseResponse.getCourses().getWebAutomation();
		for(WebAutomation webautomation : webAutomationList) {
			al.add(webautomation.getCourseTitle());
			System.out.println("WebAutomation Courses Title "+count+++" : "+webautomation.getCourseTitle());
		}
		List<String> asList = Arrays.asList(WebAutomationCourseTitles);
		Assert.assertTrue(al.equals(asList));
	}
}