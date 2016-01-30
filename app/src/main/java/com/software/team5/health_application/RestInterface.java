package com.software.team5.health_application;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import static com.jayway.restassured.RestAssured.*;

import org.json.JSONObject;

/**
 * Created by Paul on 30/01/2016.
 */
public class RestInterface {
    private String baseUrl;
    private String GetURL;

    public RestInterface(String url){
        this.baseUrl = url;
        RestAssured.baseURI = url;
        RestAssured.basePath = "NHS/API/";
    }
    public static JSONObject SendGetRequest(JSONObject message){
        response =
                when().
                        get("/rides").
                        then().
                        contentType(ContentType.JSON).  // check that the content type return from the API is JSON
                        extract().response();
    }

}
