package com.group6.MuzixRestAssured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AdminMusicController {
	
	private String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MzU3OTg3NCwiaWF0IjoxNjkzNTQzODc0fQ.vqvraUGaNg5-HSlzkoovpFumW-FqMxV_PGgc4-6dQto";
	@Test
	  public void AddNewMusic() {
	      JSONObject req = new JSONObject();
	      req.put("title", "dhere dhere se");
	      req.put("genre", "Unknown");
	      req.put("album", "unknown");
	      req.put("artist", "Hrithik");
	      baseURI="http://localhost:8090/muzix/admin/";
	      given().header("Content-Type", "application/json")

	      .header("Authorization", this.token)

	      .body(req.toJSONString()).when().post("/add").then().statusCode(201).log().all();
	  }
	
	@Test
	  public void ListAllMusic() { 
	      
	      baseURI = "http://localhost:8090/muzix/admin/all";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get().
		  then().
		  statusCode(200).log().all();
	}
	
	@Test
	  public void GetMusicById() {
		baseURI = "http://localhost:8090/muzix/admin/3";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get().
		  then().
		  statusCode(200).log().all();
	}
	
	
	@Test
	  public void DeleteMusicById() {
	      JSONObject req = new JSONObject();
	      baseURI="http://localhost:8090/muzix/admin";
	      given()
	      .header("Content-Type","application/json")
		  .header("Authorization", this.token)
		  .contentType(ContentType.JSON).accept(ContentType.JSON)
		  .when()
		  .delete("/6")
		  .then()
		  .statusCode(200)
		  .log().all();
	  }
	
	@Test
	  public void UpdateMuiscById() {
	      JSONObject req = new JSONObject();
	      req.put("title", "Kaisi ahi ye dooriyan");
	      req.put("album", "kuch baate ankahin si");
	      req.put("artist", "JJ");
	      req.put("genre", "rajni");
	      baseURI="http://localhost:8090/muzix/admin";

	      given()
	      .header("Authorization", this.token)
	      .contentType(ContentType.JSON).
	      accept(ContentType.JSON)
	      .body(req.toJSONString())
	      .when()
	      .put("/4")
	      .then()
	      .statusCode(200)
	      .log().all();
	  }
}
