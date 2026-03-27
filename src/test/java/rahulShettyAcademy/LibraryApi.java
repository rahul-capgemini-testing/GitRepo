package rahulShettyAcademy;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class LibraryApi {
	String bookID;
	// @Test(dataProvider="addBookData")
	// public void postBook(String bookName, String isbn, String aisle, String
	// author) {
	@Test
	public void postBook() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166";
		// addBook
		String response = given().header("Content-Type", "application/json")
				// .body(CommonCode.addBook(bookName,isbn,aisle,author))
				.body(new String(Files.readAllBytes(
						Paths.get("C:\\Users\\urahul\\eclipse-workspace\\RestAssuredAutomation\\addBook.json"))))
				.when().post("Library/Addbook.php").then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(response);
		System.out.println("----------------------------");
		bookID = js.getString("ID");
		System.out.println(bookID);

		// getBook
		given().queryParam("ID", bookID).when().get("/Library/GetBook.php").then().log().all();
		System.out.println("----------------------------");

		// deleteBook
		String deleteResponse = given().header("Content-Type", "application/json").body(CommonCode.deleteBook(bookID))
				.when().post("Library/DeleteBook.php").then().log().all().extract().asString();
		js = CommonCode.rawToJson(deleteResponse);
		Assert.assertEquals("book is successfully deleted", js.getString("msg"));
		System.out.println("----------------------------");
	}

	@DataProvider(name = "addBookData")
	public Object[][] addBookData() {
		return new Object[][] { { "Learn Appium Automation with Java", "bcd", "227", "John foe" },
				{ "Theory Of Realitivity", "bca", "227", "Rahul" },
				{ "Last resource of Commonsense", "bdc", "227", "Rahul" } };
	}
}
