package APIPROJECT.apiproject;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class swaggerapi {
	@Test
	public void createuser(ITestContext val)
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		JSONObject obj =new JSONObject();
		obj.put("username","pahadiguy");
		obj.put("firstName","Abhinai");
		obj.put("lastName","Tamang");
		obj.put("email","abhi@gmail.com");
		obj.put("password","1234567");
		obj.put("phone","9876542189");
		obj.put("userStatus","0");
		System.out.println(obj.toJSONString());
		given()
		.contentType(ContentType.JSON)
		.body(obj.toJSONString()).
		when()
		.post("/user").
		then()
		.statusCode(200)
		.log().all();
		String usnm="pahadiguy";
		val.setAttribute("username", usnm);
		String pswd="1234567";
		val.setAttribute("password", pswd);
		}
	@Test(dependsOnMethods="createuser")
	public void modifyuser(ITestContext val)
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		String usnm= val.getAttribute("username").toString();
		JSONObject obj =new JSONObject();
		obj.put("username","pahadiguy");
		obj.put("firstName","Abhinai");
		obj.put("lastName","Yonzone");
		obj.put("email","ibm@gmail.com");
		obj.put("password","1234567");
		obj.put("phone","9876542189");
		obj.put("userStatus","0");
		System.out.println(obj.toJSONString());
		given()
		.contentType(ContentType.JSON)
		.body(obj.toJSONString()).
		when()
		.put("/user/"+usnm).
		then()
		.statusCode(200)
		.log().all();
		
	}
	@Test(dependsOnMethods="modifyuser")
	public void login(ITestContext val)
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		String usnm= val.getAttribute("username").toString();
		String pswd= val.getAttribute("password").toString();
		given()
		.queryParam("username",usnm)
		.queryParam("password",pswd)
		.get("/user/login").
		then()
		.statusCode(200)
		.log().all();
		
	}
	
	@Test(dependsOnMethods="login")
	public void logout()
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		given()
		.get("/user/logout").
		then()
		.statusCode(200)
		.log().all();
	}
	
	@Test(dependsOnMethods="logout")
	public void delete(ITestContext val)
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		String usnm=val.getAttribute("username").toString();
		given()
		.delete("/user/"+usnm).
		then()
		.statusCode(200)
		.log().all();
	}
}


