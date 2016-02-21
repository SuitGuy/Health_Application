


package com.software.team5.health_application;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView mainListView;
    MainListViewAdapter dataAdapter;
    SharedPreferences sharedPreferences;

    //Load list items from preference
    public void loadListItems(MainListViewAdapter dataAdapter, SharedPreferences sharedPreferences){
        // Load preference
        if (sharedPreferences.getBoolean(getString(R.string.str_btn_glucose),false)){
            dataAdapter.add(new MainListViewProvider(
                            R.mipmap.ic_blood_glucose,getString(R.string.str_btn_glucose),"1"));
        }
        if (sharedPreferences.getBoolean(getString(R.string.str_btn_oxygen),false)){
            dataAdapter.add(new MainListViewProvider(
                    R.mipmap.ic_blood_oxygen,getString(R.string.str_btn_oxygen),"2"));
        }
        if (sharedPreferences.getBoolean(getString(R.string.str_btn_bloodpressure),false)){
            dataAdapter.add(new MainListViewProvider(
                    R.mipmap.ic_blood_pressure,getString(R.string.str_btn_bloodpressure),"3"));
        }
        if (sharedPreferences.getBoolean(getString(R.string.str_btn_breath_rate),false)){
            dataAdapter.add(new MainListViewProvider(
                    R.mipmap.ic_breath_rate,getString(R.string.str_btn_breath_rate),"78"));
        }
        if (sharedPreferences.getBoolean(getString(R.string.str_btn_heartrate),false)){
            dataAdapter.add(new MainListViewProvider(
                    R.mipmap.ic_heart_rate,getString(R.string.str_btn_heartrate),"98"));
        }
        if (sharedPreferences.getBoolean(getString(R.string.str_btn_sleep),false)){
            dataAdapter.add(new MainListViewProvider(
                    R.mipmap.ic_sleep,getString(R.string.str_btn_sleep),"100"));
        }
        dataAdapter.add(new MainListViewProvider(
                R.mipmap.ic_plus,getString(R.string.str_btn_add),""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Create listView and set click listener
        mainListView = (ListView)findViewById(R.id.MainListView);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Getting the Container Layout of the ListView (in listview_row_layout.xml)
                RelativeLayout relativeLayoutParent = (RelativeLayout) view;
                // Getting the TextView name
                TextView nameTextView = (TextView) relativeLayoutParent.getChildAt(1);
                //Toast.makeText(getBaseContext(), nameTextView.getText().toString(), Toast.LENGTH_SHORT).show();

                String textViewString = nameTextView.getText().toString();
                if (textViewString.equals(getString(R.string.str_btn_glucose)))
                    startActivity(new Intent(MainActivity.this, BloodGlucoseActivity.class));
                else if (textViewString.equals(getString(R.string.str_btn_oxygen)))
                    startActivity(new Intent(MainActivity.this, BloodOxygenActivity.class));
                else if (textViewString.equals(getString(R.string.str_btn_bloodpressure)))
                    startActivity(new Intent(MainActivity.this, BloodPressureActivity.class));
                else if (textViewString.equals(getString(R.string.str_btn_breath_rate)))
                    startActivity(new Intent(MainActivity.this, BreathRateActivity.class));
                else if (textViewString.equals(getString(R.string.str_btn_heartrate)))
                    startActivity(new Intent(MainActivity.this, HeartRateActivity.class));
                else if (textViewString.equals(getString(R.string.str_btn_sleep)))
                    startActivity(new Intent(MainActivity.this, SleepActivity.class));
                else if(textViewString.equals(getString(R.string.str_btn_add))) {
                    Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                    intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GeneralPreferenceFragment.class.getName());
                    intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "Activity incorrect!", Toast.LENGTH_SHORT).show();
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Set default preference only once
        if(!sharedPreferences.getBoolean("firstTime",false)){
            Toast.makeText(getApplicationContext(), "No preference file!", Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", true);
            editor.putBoolean(getString(R.string.str_btn_glucose),true);
            editor.putBoolean(getString(R.string.str_btn_oxygen),true);
            editor.putBoolean(getString(R.string.str_btn_bloodpressure),true);
            editor.putBoolean(getString(R.string.str_btn_sleep),true);
            editor.apply();
        }
//        else {
//            Toast.makeText(getApplicationContext(), "Have preference file!", Toast.LENGTH_LONG).show();
//        }

        //Create data adapter and assign it to listView
        dataAdapter = new MainListViewAdapter(getApplicationContext(),R.layout.listview_row_layout);
        mainListView.setAdapter(dataAdapter);
        //Load Items
        loadListItems(dataAdapter,sharedPreferences);
    }
    @Override
    protected void onResume(){
        super.onResume();
        dataAdapter.clear();
        loadListItems(dataAdapter,sharedPreferences);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class AsyncGetRequest extends AsyncTask<Void, Void, Void>
    {
        //TextView view = (TextView) findViewById(R.id.text_heart_rate);
        String baseUrl = "http://192.168.0.3:8080/listMedical";
        HttpResponse response = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread

        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            // Create http client object to send request to server
            HttpClient client = new DefaultHttpClient();
            // Create URL string
            String URL = baseUrl;
            // Create Request to server and get response
            HttpGet httpget= new HttpGet();



            try {
                httpget.setURI(new URI(URL));
                response = client.execute(httpget);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            String res = "";
            try {
                InputStream istream = response.getEntity().getContent();
                if(istream != null){
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(istream));
                    String line = "";
                    while((line = bufferedReader.readLine()) != null)
                        res += line;

                    istream.close();
                }else {
                    //view.setText("it Done brokeded");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //view.setText(res);

        }
    }
    private class AsyncPostRequest extends AsyncTask<Void, Void, Void>
    {

        //TextView view = (TextView) findViewById(R.id.text_heart_rate);
        String baseUrl = "http://192.168.0.3:8080/getMedical";
        HttpResponse response = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread

        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            // Create http client object to send request to server
            HttpClient client = new DefaultHttpClient();
            // Create URL string


            //url with the post data
            HttpPost httpost = new HttpPost();
            // String jsonstr = getString("{ }");



            try {
                JSONObject holder = new JSONObject();
                StringEntity se = new StringEntity("");
                httpost.setEntity(se);
                httpost.setHeader("Accept", "application/json");
                httpost.setHeader("Content-type", "application/json");
                httpost.setURI(new URI(baseUrl));
                response = client.execute(httpost);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } /*catch (JSONException e2) {
                e2.printStackTrace();
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            String res = "";
            try {
                InputStream istream = response.getEntity().getContent();
                if(istream != null){
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(istream));
                    String line = "";
                    while((line = bufferedReader.readLine()) != null)
                        res += line;

                    istream.close();
                }else {
                    //view.setText("it Done brokeded");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //view.setText(res);

        }

    }
}
