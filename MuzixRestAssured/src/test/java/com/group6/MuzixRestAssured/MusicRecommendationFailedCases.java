package com.group6.MuzixRestAssured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class MusicRecommendationFailedCases {
	private String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZlbiIsImV4cCI6MTY5Mzg0MjM4MywiaWF0IjoxNjkzODA2MzgzfQ.CZCV5QoOatvJktEiE3IWS3a9u8dx8_LfxYYDSaKstps";
	@Test
	  public void searchByAlbum() {
		  baseURI = "http://localhost:8090/muzix/album";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get("/RRR").
		  then().
		  statusCode(404).log().all();
	  }
	
	@Test
	  public void searchByArtist() {
		  baseURI = "http://localhost:8090/muzix/artist";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get("/Prabhas").
		  then().
		  statusCode(400).log().all();
	  }
	
	@Test
	  public void searchByGenre() {
		  baseURI = "http://localhost:8090/muzix/genre";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get("/mass").
		  then().
		  statusCode(404).log().all();
	  }
	
	
	@Test
	  public void ListMusicForUser() { 
	      
	      baseURI = "http://localhost:8090/muzix/admin/all";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get().
		  then().
		  statusCode(404).log().all();
	}
	
	@Test
	  public void GetMusicForUserById() {
		baseURI = "http://localhost:8090/muzix/admin/3";
		  given().header("Content-Type", "application/json")
		  .header("Authorization", this.token)
		  .get().
		  then().
		  statusCode(404).log().all();
	}
}
