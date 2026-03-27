package rahulShettyAcademy;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class JiraIssueAttachment {
	@Test
	public void jira() {
		RestAssured.baseURI = "https://rahulr5366.atlassian.net/";
		String createIssueResponse = 
		given()
		.header("Content-Type","application/json")
		.header("Authorization","Basic cmFodWxyNTM2NkBnbWFpbC5jb206QVRBVFQzeEZmR0YwSzlhcHVmQ3ZMYkRDdmUxQjRlcUdYa0JZTVFMY2kwTGRwdnBfOUFFb1ZqZXp4dHhWanhxc3JMMHhMU0ZWMVM4bnJkM3FGRlZCb0o5VzhFejU0TUxWSlJKM3FCUVVRWDRRSkxlTm83Wl9nck1BMlotV1ItazVGeFV5LUVzcHFabnpEVzNWU0xLbDRvY01rejYwYmdycThmX05vWnVxN2JmUHI3WHR3b0pBUmE4PURFQkFDNjU3")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Windows automation Bug\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}")
		.when().post("rest/api/3/issue")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(createIssueResponse);
		String issueID = js.getString("id");
		
		//sending attachment
		given()
		.pathParam("key", issueID)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic cmFodWxyNTM2NkBnbWFpbC5jb206QVRBVFQzeEZmR0YwSzlhcHVmQ3ZMYkRDdmUxQjRlcUdYa0JZTVFMY2kwTGRwdnBfOUFFb1ZqZXp4dHhWanhxc3JMMHhMU0ZWMVM4bnJkM3FGRlZCb0o5VzhFejU0TUxWSlJKM3FCUVVRWDRRSkxlTm83Wl9nck1BMlotV1ItazVGeFV5LUVzcHFabnpEVzNWU0xLbDRvY01rejYwYmdycThmX05vWnVxN2JmUHI3WHR3b0pBUmE4PURFQkFDNjU3")
		.multiPart("file",new File("C:\\Users\\urahul\\Desktop\\Dektop_Internet_Issue.jpg"))
		.post("rest/api/3/issue/{key}/attachments")
		.then().log().all();
	}
}
