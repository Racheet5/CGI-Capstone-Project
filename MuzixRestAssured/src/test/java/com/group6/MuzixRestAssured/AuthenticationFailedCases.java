package com.group6.MuzixRestAssured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class AuthenticationFailedCases {

	
	@Test(priority=1) // If same user try to signUp with same userName and email
	  public void Admin_SignUp_Fail() {
		  JSONObject req = new JSONObject();
			 
		  req.put("userName", "admin3");
		  req.put("email", "admin3@gmail.com");
		  req.put("password", "admin3");
		  req.put("imageUrl", "image/admin.jpg");
		  req.put("role", "admin");
		  baseURI="http://localhost:8090/user";
		  given().header("Content-Type", "application/json").body(req.toJSONString()).when().post("/signup").then().statusCode(409).log().all();
	  }
	
	@Test(priority=2) // If userName or Password is invalid
	  public void Admib_Login_Fail() {
		  JSONObject req = new JSONObject();
			 
		  req.put("userName", "admin");
		  req.put("password", "admin");
		  baseURI="http://localhost:8090/user";
		  given().header("Content-Type", "application/json").body(req.toJSONString()).when().post("/signin").then().statusCode(500).log().all();
	  }
	
	@Test
	  public void ListAllMusic() { 
	      
	      baseURI = "http://localhost:8090/muzix/admin/all";
		  given().header("Content-Type", "application/json")
		  .get().
		  then().
		  statusCode(403).log().all();
	}
	
	@Test
	  public void ListMusicForUser() { 
	      
	      baseURI = "http://localhost:8090/muzix/admin/all";
		  given().header("Content-Type", "application/json")
		  .get().
		  then().
		  statusCode(403).log().all();
	}
	
	@Test
	  public void ListAllMyFavourites() {
		  baseURI = "http://localhost:8090/muzix";
		  given().header("Content-Type", "application/json")
		  .get("/favourite/3").
		  then().
		  statusCode(403).log().all();
	  }
}

