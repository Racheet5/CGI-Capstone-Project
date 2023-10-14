package com.group6.MuzixRestAssured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class UserFavouritePlaylist {
	
	private String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZlbiIsImV4cCI6MTY5MzUyMTQ0MiwiaWF0IjoxNjkzNDg1NDQyfQ.EU1vQMQ0rjqSHdWW0xuV9t9nSWnnLDJLw23jjgwQogM";
	
	@Test
	  public void AddToFavouritePlaylist() {
		  JSONObject req = new JSONObject();
		  req.put("userId", "1");
		  req.put("musicId", "4");
		  baseURI="http://localhost:8090/muzix/favourite";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .body(req.toJSONString()).when().post("/add").then().statusCode(201).log().all();
	  }
	
	@Test
	  public void ListAllMyFavourites() {
		  baseURI = "http://localhost:8090/muzix";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get("/favourite/3").
		  then().
		  statusCode(200).log().all();
	  }
	
	@Test
	  public void RemoveMusicFromFavourite() {
		  JSONObject req = new JSONObject();
		  baseURI="http://localhost:8090/muzix/favourite";
		  given()
		  .header("Content-Type","application/json")
		  .header("Authorization", this.token)
		  .contentType(ContentType.JSON).accept(ContentType.JSON)
		  .when()
		  .delete("/3")  //pass the favourite music id
		  .then()
		  .statusCode(200)
		  .log().all();

	  }
}
