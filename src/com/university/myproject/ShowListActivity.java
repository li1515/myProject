package com.university.myproject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
 
public class ShowListActivity extends ListActivity {
	
	 static  JSONArray jArray;
	 private EditText et;
	 int textlength = 0;
	// Progress Dialog
	 private ProgressDialog pDialog;
	 private ArrayList<String> array_sort = new ArrayList<String>();
	 
	 
	 
	 public ArrayList<String> returnTitle()
	  {
		  String result = "";
		  ArrayList<String> strList = new ArrayList<String>();
			//the year data to send/*
			/*ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("name","lat"));*/
			 
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			//http post
			try{
					 
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://buddydroid.com/myFile3.php");
			        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpResponse response = httpclient.execute(httppost);
			        HttpEntity entity = response.getEntity();
			        InputStream is = entity.getContent();
			        
			         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),20);
			        StringBuilder sb = new StringBuilder();
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			        }
			        is.close();
			 
			        result=sb.toString();
			        
			}catch(Exception e){
			        Log.e("log_tag", "Error in http connection "+e.toString());
			}
		   
			try{
		        jArray = new JSONArray(result);
		        // ITERATE THROUGH AND RETRIEVE CLUB FIELDS
	            int n = jArray.length();
	            for (int i = 0; i < n; i++) {
	                // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
	                JSONObject jo = jArray.getJSONObject(i);
	                 
	                // RETRIEVE EACH JSON OBJECT'S FIELDS
	                String lat = jo.getString("title");
	                // CONVERT DATA FIELDS TO CLUB OBJECT
	                strList.add(lat);
		        
		}}catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
			return strList;
	  }
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
         
        //lv = (ListView) findViewById(R.id.list);
        final ListView lv = getListView();
		et = (EditText) findViewById(R.id.EditText01);
		
		
		String[] listview_array = new String[returnTitle().size()];
		listview_array = returnTitle().toArray(listview_array);
		
		 // storing string resources into Array
        final String[] objects = listview_array;
		
		lv.setAdapter(new ArrayAdapter<String>(this,
				R.layout.list_item, objects));

		et.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// Abstract Method of TextWatcher Interface.
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Abstract Method of TextWatcher Interface.
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength = et.getText().length();
				array_sort.clear();
				for (int i = 0; i < objects.length; i++) {
					if (textlength <= objects[i].length()) {
						/*if (et.getText()
								.toString()
								.equalsIgnoreCase(
										(String) objects[i].subSequence(
												0, textlength)*/
						if(objects[i].toLowerCase().contains(
                                et.getText().toString().toLowerCase().trim()))
						{
							array_sort.add(objects[i]);
						}
					}
				}
				lv.setAdapter(new ArrayAdapter<String>(ShowListActivity.this,
						R.layout.list_item, array_sort));
			}
		});
       
         
        // Binding resources Array to ListAdapter
        //this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, objects));
         
        //ListView lv = getListView();
 
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          @Override
		public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
        	
              // selected item 
              String obj = ((TextView) view).getText().toString();
               
              // Launching new Activity on selecting single List Item
              Intent i = new Intent(getApplicationContext(), ShowMapActivity.class);
              // sending data to new activity
              i.putExtra("object", obj);
              startActivity(i);
             
          }
        });
    }
}