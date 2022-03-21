package APIPROJECT.apiproject;



import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import Pojo.pojoclass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class localhostapi {
	@Test
	public void createuser(ITestContext val) throws JsonProcessingException
	{
		RestAssured.baseURI="http://localhost:3000";
		pojoclass obj=new pojoclass();
		obj.setUsername("valentinorossie");
		obj.setFirstName("valentino");
		obj.setLastName("rossie");
		obj.setEmail("v@gmail.com");
		obj.setPassword("1234567");
		obj.setPhone("9832422657");
		obj.setUserStatus("0");
		ObjectMapper objmapper= new ObjectMapper();
		Response resp=given()
		.contentType(ContentType.JSON)
		.body(objmapper.writeValueAsString(obj)).
		when()
		.post("/user").
		then()
		.statusCode(201)
		.log().all()
		.extract()
		.response();
		String id=resp.jsonPath().getString("id");
		System.out.println(id);
		val.setAttribute("id",id);
		String usnm=resp.jsonPath().getString("username");
		System.out.println(usnm);
		val.setAttribute("username",usnm);
		String pswd=resp.jsonPath().getString("password");
		System.out.println(pswd);
		val.setAttribute("password",pswd);

	}
	@Test(dependsOnMethods="createuser")
	public void modifyuser(ITestContext val) throws JsonProcessingException
	{
		RestAssured.baseURI="http://localhost:3000";
		String id=val.getAttribute("id").toString();
		pojoclass obj=new pojoclass();
		obj.setUsername("valentinorossie");
		obj.setFirstName("marquez");
		obj.setLastName("46");
		obj.setEmail("v@gmail.com");
		obj.setPassword("1234567");
		obj.setPhone("9832489076");
		obj.setUserStatus("0");
		ObjectMapper objmapper= new ObjectMapper();
		Response resp=given()
		.contentType(ContentType.JSON)
		.body(objmapper.writeValueAsString(obj)).
		when()
		.put("/user/"+id).
		then()
		.statusCode(200)
		.log().all()
		.extract()
		.response();

	}
	@Test(dependsOnMethods="modifyuser")
	public void login(ITestContext val)
	{
		RestAssured.baseURI="http://localhost:3000";
		String usnm= val.getAttribute("username").toString();
		String pswd= val.getAttribute("password").toString();
		String id=val.getAttribute("id").toString();
		given()
		.queryParam("username",usnm)
		.queryParam("password",pswd)
		.get("/user/"+id).
		then()
		.statusCode(200)
		.log().all();
		
	}
	
//	@Test(dependsOnMethods="login")
//	public void logout()
//	{
//		RestAssured.baseURI="http://localhost:3000";
//		given()
//		.get("/user").
//		then()
//		.statusCode(200)
//		.log().all();
//	}
	@Test(dependsOnMethods="login")
	public void delete(ITestContext val)
	{
		RestAssured.baseURI="http://localhost:3000";
		String id=val.getAttribute("id").toString();
		given()
		.delete("/user/"+id).
		then()
		.statusCode(200)
		.log().all();
	}
	

	
}
