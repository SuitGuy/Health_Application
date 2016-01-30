package com.software.team5.health_application;



import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Paul on 30/01/2016.
 */
public class RestInterface implements Runnable {
    private String baseUrl;
    private String GetURL;
    private TextView view;

    public RestInterface(TextView view, String baseUrl, String response ){
        this.baseUrl = baseUrl;
        this.view = view;
    }



    public void run(){
        HttpResponse response = null;
    try {
        // Create http client object to send request to server
        HttpClient client = new DefaultHttpClient();
        // Create URL string
        String URL = baseUrl;
        // Create Request to server and get response
        HttpGet httpget= new HttpGet();
        httpget.setURI(new URI(URL));
        response = client.execute(httpget);

        view.setText(response.toString());
        view.notify();
    } catch (URISyntaxException e) {
        e.printStackTrace();
    }
    catch (ClientProtocolException e) {
        // TODO Auto-generated catch block
    } catch (IOException e) {
        // TODO Auto-generated catch block
    }

}

}
