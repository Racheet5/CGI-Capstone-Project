package com.group6.MuzixRestAssured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class Authentication {
	
	@Test(priority=1)
	  public void Admin_SignUp() {
		  JSONObject req = new JSONObject();
			 
		  req.put("userName", "admin3");
		  req.put("email", "admin3@gmail.com");
		  req.put("password", "admin3");
		  req.put("imageUrl", "image/dd.jpg");
		  req.put("role", "admin");
		  baseURI="http://localhost:8090/user";
		  given().header("Content-Type", "application/json").body(req.toJSONString()).when().post("/signup").then().statusCode(201).log().all();
	  }
	
	
	@Test(priority=3) // If userName and Password are valid
	  public void Admib_Login() {
		  JSONObject req = new JSONObject();
			 
		  req.put("userName", "admin3");
		  req.put("password", "admin3");
		  baseURI="http://localhost:8090/user";
		  given().header("Content-Type", "application/json").body(req.toJSONString()).when().post("/signin").then().statusCode(200).log().all();
	  }

}
