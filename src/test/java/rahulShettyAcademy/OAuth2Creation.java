package rahulShettyAcademy;
import static io.restassured.RestAssured.given;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.restassured.path.json.JsonPath;

public class OAuth2Creation {
	
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--guest");
		WebDriver driver = new ChromeDriver(chromeOptions);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifymrstan");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("rahulcapgeminitesting");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("input[type='password']"))).sendKeys("Capgemini@123");
		//driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Capgemini@123");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Continue']"))).click();
		Thread.sleep(Duration.ofSeconds(10));
		String code = driver.getCurrentUrl().split("&")[2].split("=")[1];
		System.out.println("code - "+code);
		//driver.quit();
		//https://rahulshettyacademy.com/getCourse.php?state=verifymrstan&iss=https%3A%2F%2Faccounts.google.com&code=4%2F0Aci98E_1HWRaR4dJG6LKgQQyWrJSYvcSsFw0QYIzljIdZVZMlpy3folL8vW8hzQYGRG7Uw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none
		
		
		String authorizationResponse = given().urlEncodingEnabled(false).queryParams("code",code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(authorizationResponse);
		String accessToken = js.getString("access_token");
		System.out.println("access_token - "+accessToken);
		
		String responseGetCourse = given().relaxedHTTPSValidation().queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.asString();
		System.out.println(responseGetCourse);
	}

}
