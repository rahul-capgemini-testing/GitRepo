package rahulShettyAcademy;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class UnderstandingJson {

	@Test
	public void JsonValidations(){
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(CommonCode.jsonCourses());
		System.out.println(js.getInt("courses.size()"));
		System.out.println(js.getInt("dashboard.purchaseAmount"));
		System.out.println(js.getString("courses[0].title"));
		int coursesSize = js.getInt("courses.size()");
		for(int i = 0; i<coursesSize; i++) {
			System.out.println(js.get("courses["+i+"].title").toString());
			System.out.println(js.getInt("courses["+i+"].price"));
		}
		for(int i = 0; i < coursesSize; i++) {
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
				System.out.println("Course "+js.getString("courses["+i+"].title")+" Price "+js.getString("courses["+i+"].price"));
				break;
			}
		}
		int sum = 0;
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		for(int i=0; i<coursesSize; i++) {
			sum += (js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies"));
		}
		Assert.assertEquals(sum, purchaseAmount);
	}

}
