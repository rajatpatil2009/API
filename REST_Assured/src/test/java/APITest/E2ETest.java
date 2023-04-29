package APITest;

import io.restassured.RestAssured;
import apiengin.AddUser;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class E2ETest {
	
	
	
	public static void createUser()
	{
		RestAssured.baseURI="https://bookstore.toolsqa.com";		
		JSONObject param = new JSONObject();
		param.put("userName","rajatpatil1");
		param.put("password", "Rat2@12345");
		
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		request.body(param.toString());
		Response response = request.post("/Account/v1/User");		
		String JSONReposonse = response.asString();		
		String userID = response.getBody().jsonPath().getString("userID");
	}
	public static void createToken()
	{
		RestAssured.baseURI="https://bookstore.toolsqa.com";		
		JSONObject param = new JSONObject();
		param.put("userName","rajatpatil");
		param.put("password", "Rat2@12345");
		
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		request.body(param.toString());
		Response response = request.post("/Account/v1/User");		
		String JSONReposonse = response.asString();		
		String token = response.getBody().jsonPath().getString("token");
	}

	public static void main(String[] args) {


	
		RestAssured.baseURI="https://bookstore.toolsqa.com";						
		RequestSpecification request = RestAssured.given();	
		request.header("Content-Type","application/json");
		
		
		
		
		AddUser adduser = new AddUser("rajatpatil1","Rat2@12345");
		
		request.body(adduser);
		Response createUserResponse = request.post("/Account/v1/User");		
		//String createUserJSONReposonse = createUserResponse.asString();		
		String userID = createUserResponse.getBody().jsonPath().getString("userID");
		
		// Generate token : post
		
		request.body(adduser.toString());
		Response response = request.post("/Account/v1/GenerateToken");		
		String JSONReposonse = response.asString();		
		String token = response.getBody().jsonPath().getString("token");	
		System.out.println(JSONReposonse);
		System.out.println(response.getStatusCode());
		
	// Get book : Get
		
		Response bookresponse = request.get("/BookStore/v1/Books");
		String books = bookresponse.asString();
		System.out.println(books);
		List<Map<String, String>> book = JsonPath.from(books).get("books");
		String bookName = book.get(0).get("isbn");
		System.out.println(bookName);
		
	// Add reqest
		
		request.header("Content-Type","application/json");
		request.header("Authorization","Bearer"+token);
		
		request.body("{ \"userId\": \"" + "f52305fe-86b8-4cc6-a787-6e2ca90b2d5f" + "\", " +
                "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookName + "\" } ]}");
		Response addbookResponse = request.post("/BookStore/v1/Books");
     System.out.println(addbookResponse.asString());
			//	Assert.assertEquals(addbookResponse.statusCode(), "201");
		
		
		

				

	}

}
