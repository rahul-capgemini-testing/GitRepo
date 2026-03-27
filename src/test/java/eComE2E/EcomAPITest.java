package eComE2E;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class EcomAPITest {

	public static void main(String[] args) {
		RequestSpecification res = new RequestSpecBuilder()
		.setBaseUri("https://rahulshettyacademy.com/api/ecom")
		.setContentType(ContentType.JSON).build();
		
		LoginPojo lp = new LoginPojo();
		lp.setUserEmail("rahulr5366@gmail.com");
		lp.setUserPassword("Rahul@123");
		
		RequestSpecification reqLogin = given().relaxedHTTPSValidation()
				.log().all().spec(res).body(lp);
		LoginResponse lr =reqLogin.when().post("/auth/login").then().log().all()
				.extract()
		.as(LoginResponse.class);
		System.out.println(lr.getToken());
		System.out.println(lr.getUserId());
		String authToken = lr.getToken();
		
		//CreateProduct
		RequestSpecification addProduct = new RequestSpecBuilder()
		.setBaseUri("https://rahulshettyacademy.com/api/ecom")
		.addHeader("Authorization", authToken).build();
		
		RequestSpecification addPro = given().relaxedHTTPSValidation()
				.log().all().spec(addProduct)
		.param("productName", "Casio 2200DD")
		.param("productAddedBy",lr.getUserId())
		.param("productCategory", "fashion")
		.param("productSubCategory", "Watches")
		.param("productPrice", "22900")
		.param("productDescription", "Casio 2200DD Solar Watch")
		.param("productFor", "Men")
		.multiPart("productImage",new File("C:\\Users\\urahul\\Desktop\\Casio.jpg"));
		
		String addProductResponse = addPro.when().post("/product/add-product")
		.then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.getString("productId");
		System.out.println(productId);
		
		//Create Order
		OrderDetailPojo odp = new OrderDetailPojo();
		odp.setCountry("India");
		odp.setProductOrderedId(productId);
		List<OrderDetailPojo> orderList = new ArrayList<OrderDetailPojo>();
		orderList.add(odp);
		OrderPojo op = new OrderPojo();
		op.setOrders(orderList);
		RequestSpecification createProduct = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com/api/ecom")
				.addHeader("Authorization", authToken)
				.setContentType(ContentType.JSON).build();
		RequestSpecification createOrder = given().relaxedHTTPSValidation()
				.log().all()
				.spec(createProduct).body(op);
		String createOrderResponse = createOrder.when().post("order/create-order")
		.then().log().all().extract().response().asString();
		JsonPath jp = new JsonPath(createOrderResponse);
		String orderId = jp.getString("orders");
		System.out.println(orderId);
		
		//Delete Order
		RequestSpecification deleteProduct = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com/api/ecom")
				.addHeader("Authorization", authToken).addPathParam("productId", productId).build();
		given().relaxedHTTPSValidation().spec(deleteProduct).when().delete("product/delete-product/{productId}")
		.then().log().all();
	}

}
