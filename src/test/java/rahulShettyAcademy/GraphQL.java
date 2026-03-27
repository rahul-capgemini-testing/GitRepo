package rahulShettyAcademy;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class GraphQL {
	@Test
	public void fetchQuerry() {
		String response = given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"#mutation {\\n#createLocation(location: {name: \\\"Bangalore\\\", type: \\\"Address\\\", dimension: \\\"123\\\"}) {\\n# id 29586\\n#}\\n#  createCharacter(character: {name: \\\"Rahul\\\", type: \\\"jovial\\\", status: \\\"Alive\\\", species: \\\"Human\\\", gender: \\\"Male\\\", originId: 29586, image: \\\"png\\\", locationId: 29586}) {\\n#    id 21915\\n#  }\\n#}\\n#query($characterId : Int!){\\n#  character(characterId: $characterId ){\\n#    id\\n#    name\\n#    gender\\n#    type\\n#    status\\n#    species\\n#    location{name,id,type}\\n#    origin{id,name,created,residents {\\n#      id\\n#    }}\\n#  }\\n#}\\n#mutation{\\n#  createEpisode(episode:{name:\\\"Rahul Learning GraphQL\\\",air_date:\\\"24-03-2026\\\",episode:\\\"21915\\\"}){\\n#    id 19928\\n#  }\\n#}\\nquery{\\n  episode(episodeId:19928){\\n    id\\n    name\\n    air_date\\n    episode\\n    created\\n  }\\n}\",\"variables\":{\"characterId\":21915}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();

		System.out.println(response);
	}

}
