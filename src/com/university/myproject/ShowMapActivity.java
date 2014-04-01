package com.university.myproject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ShowMapActivity extends FragmentActivity {

	protected static LatLng help2 = null;
	LatLng toPosition;
	LatLng fromPosition;
	Document document;
	GMapV2GetRouteDirection v2GetRouteDirection;
	MarkerOptions markerOptions;
	GoogleMap map;
	static JSONArray jArray;
	ArrayList<Double> latitList = new ArrayList<Double>();
	ArrayList<Double> lngList = new ArrayList<Double>();
	ArrayList<String> descList = new ArrayList<String>();
	ArrayList<String> titleList = new ArrayList<String>();
	//LocationTracker mGPSl = new LocationTracker(this);
	int selected;
	private Marker myMarker;
	private ImageView mDialog;
	
	// Progress Dialog
    private ProgressDialog pDialog;
	public ArrayList<Double> returnLat(String str) {
		String result = "";
		ArrayList<Double> latList = new ArrayList<Double>();
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder().permitAll().build();
		// StrictMode.setThreadPolicy(policy);

		// http post
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://buddydroid.com/myFile.php");
			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			jArray = new JSONArray(result);
			// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
			int n = jArray.length();
			for (int i = 0; i < n; i++) {
				// GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
				JSONObject jo = jArray.getJSONObject(i);

				// RETRIEVE EACH JSON OBJECT'S FIELDS
				double lat = jo.getDouble(str);
				// CONVERT DATA FIELDS TO CLUB OBJECT
				latList.add(lat);

			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return latList;
	}
	public ArrayList<Double> returnLng(String str) {
		String result = "";
		ArrayList<Double> lngList = new ArrayList<Double>();
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder().permitAll().build();
		// StrictMode.setThreadPolicy(policy);

		// http post
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://buddydroid.com/myFile4.php");
			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			jArray = new JSONArray(result);
			// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
			int n = jArray.length();
			for (int i = 0; i < n; i++) {
				// GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
				JSONObject jo = jArray.getJSONObject(i);

				// RETRIEVE EACH JSON OBJECT'S FIELDS
				double lat = jo.getDouble(str);
				// CONVERT DATA FIELDS TO CLUB OBJECT
				lngList.add(lat);

			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return lngList;
	}



	public ArrayList<String> returnDescription(String str) {
		String result = "";
		ArrayList<String> descList = new ArrayList<String>();
	
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://buddydroid.com/myFile2.php");
			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
			System.out.println(result);
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			jArray = new JSONArray(result);
			// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
			int n = jArray.length();
			for (int i = 0; i < n; i++) {
				// GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
				JSONObject jo = jArray.getJSONObject(i);

				// RETRIEVE EACH JSON OBJECT'S FIELDS
				String lat = jo.getString(str);
				// CONVERT DATA FIELDS TO CLUB OBJECT
				descList.add(lat);

			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return descList;
	}

	
	  
	public Drawable showMe(String url) { Bitmap bitmap=null;
	
	try { URL newurl  = new URL(url); 
	  bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); }
	  catch (MalformedURLException e) 
	  { // TODO Auto-generated catch block
	  e.printStackTrace(); } 
	catch (IOException e)
	  { // TODO Auto-generated
	  
		
	  e.printStackTrace(); } 
	
	bitmap=getBitmap(url); 
	Paint paint =
	  new Paint(); 
	paint.setFilterBitmap(true); 
	int targetWidth = 80; int
	  targetHeight = 80; 
	Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
	  targetHeight,Bitmap.Config.ARGB_8888); 
	  RectF rectf = new RectF(0, 0, 80,
	  80); 
	  Canvas canvas = new Canvas(targetBitmap); 
	  Path path = new Path();
	  path.addRoundRect(rectf, targetWidth, targetHeight, Path.Direction.CW);
	  canvas.clipPath(path); 
	  canvas.drawBitmap( bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), paint);
	  
	  Matrix matrix = new Matrix(); 
	  matrix.postScale(1f, 1f); 
	  Bitmap resizedBitmap = Bitmap.createBitmap(targetBitmap, 0, 0, 80, 80, matrix,
	  true); 
	  Bitmap bitmap_circle=mergeBitmaps(resizedBitmap);
      BitmapDrawable bd = new BitmapDrawable(bitmap_circle);
//	  @SuppressWarnings("deprecation") BitmapDrawable bd = new
//	  BitmapDrawable(resizedBitmap);
	  
	  
	  
	  return bd; }	
	public Bitmap mergeBitmaps(Bitmap manBitmap){

        try{

            Bitmap markerBitmap = BitmapFactory.decodeResource( this.getResources(), R.drawable.circle);
            Bitmap bmOverlay = Bitmap.createBitmap(markerBitmap.getWidth(), markerBitmap.getHeight(), markerBitmap.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            Matrix matrix = new Matrix();
            matrix.postScale(1f, 1f);
            canvas.drawBitmap(markerBitmap, matrix, null);
            canvas.drawBitmap(manBitmap, (markerBitmap.getWidth()-manBitmap.getWidth())/2, 
            	    (markerBitmap.getHeight()-manBitmap.getHeight())/2, null);
            return bmOverlay;

        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

    }
	
	public Bitmap getBitmap(String url) {
	/*	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);*/
		Bitmap bm = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.connect();
			is = conn.getInputStream();
			bis = new BufferedInputStream(is, 8192);
			bm = BitmapFactory.decodeStream(bis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bm;
	}
	

	public String getAddress (Location location) {
		String addressString = "No address found";
	
		 if (location != null) {
		        double lat = location.getLatitude();
		        double lng = location.getLongitude();
		        Geocoder gc = new Geocoder(ShowMapActivity.this, Locale.getDefault());
		        try {
		            List<Address> addresses  = gc.getFromLocation(lat, lng, 1);
		            if (addresses.size() == 1) {
		                addressString="";
		                Address address = addresses.get(0);
		                addressString = addressString + address.getAddressLine(0) + "\n";
		            }} catch (IOException ioe) {
		                Log.e("Geocoder IOException exception: ", ioe.getMessage());}}
		 return addressString;
		        }
	class Load extends AsyncTask<String, String, String> {
		  @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(ShowMapActivity.this);
	            pDialog.setMessage("Отримання данних з сервера. Почекайте будь-ласка...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	    	
	        }
		@Override
		protected String doInBackground(String... arg0) {
			Looper.prepare();
			ShowMapActivity m = new ShowMapActivity();
			ShowListActivity l = new ShowListActivity();
			for (int i = 0; i < m.returnLng("lng").size(); i++) {
			lngList.add(m.returnLng("lng").get(i));
			descList.add(m.returnDescription("description").get(i));
			latitList.add(m.returnLat("lat").get(i));
			titleList.add(l.returnTitle().get(i));
			}
			
			return null;
				
		}
	
		    protected void onPostExecute(String file_url) {
		      
		       int g = 0;
				Intent i1 = getIntent();
				// getting attached intent data
				String selObj = i1.getStringExtra("object");
				map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
						.getMap();
				
				for (int i = 0; i < latitList.size(); i++) {
					// LatLng help = new LatLng(returnLat().get(i), returnLng().get(i));
					
//					 Bitmap iconn = ((BitmapDrawable)showMe("https://cbks1.google.com/cbk?output=thumbnail&cb_client=maps_sv&thumb=2&thumbfov=60&ll="+latitList.get(i)+","+lngList.get(i)+"&yaw=326.1&thumbpegman=1&w=300&h=118")).getBitmap();
					 Bitmap iconl = ((BitmapDrawable)showMe("http://buddydroid.com/"+i+".jpg")).getBitmap();
					myMarker = map.addMarker(new MarkerOptions()
							.position(
									new LatLng(latitList.get(i), lngList.get(i)))
							.title(titleList.get(i))
							.snippet(descList.get(i))
							//.icon(BitmapDescriptorFactory
									//.fromResource(R.drawable.agency)));
									.icon(BitmapDescriptorFactory.fromBitmap(iconl)));
					
					if (selObj.equals(titleList.get(i))) {
						// Move the camera instantly to hamburg with a zoom of 15.
						g = i;
						// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000,
						// null);
					}
					LatLng help = new LatLng(latitList.get(g), lngList.get(g));
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(help, 15));
					// Zoom in, animating the camera.
					// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
					 pDialog.dismiss();
				}
		    }	
		    @Override
		    protected void onCancelled() {
		      super.onCancelled();}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
	new Load().execute();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);
		v2GetRouteDirection = new GMapV2GetRouteDirection();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		
		
		/*
		for (int i = 0; i < latitList.size(); i++) {
			// LatLng help = new LatLng(returnLat().get(i), returnLng().get(i));
			map.addMarker(new MarkerOptions()
					.position(
							new LatLng(latitList.get(i), lngList.get(i)))
					.title(titleList.get(i))
					.snippet(descList.get(i))
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.agency)));
			// Move the camera instantly to hamburg with a zoom of 15.
			// map.moveCamera(CameraUpdateFactory.newLatLngZoom(help, 5));
			// Zoom in, animating the camera.
			// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
			//Log.e("log_tag", "anuka " + returnTitle().get(i));
			if (selObj.equals(titleList.get(i))) {
				// Move the camera instantly to hamburg with a zoom of 15.
				g = i;
				// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000,
				// null);
			}
			LatLng help = new LatLng(latitList.get(g), lngList.get(g));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(help, 15));
			// Zoom in, animating the camera.
			// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}*/
		/*int g = 0;
		Intent i2 = getIntent();
		// getting attached intent data
		String selObj2 = i2.getStringExtra("object");
		LocationTracker mGPS = new LocationTracker(this);
		for (int i = 0; i < returnLat("lat").size(); i++) {
			if (selObj2.equals(new ShowListActivity().returnTitle().get(i))) {
				
				g = i;
			
			}
			if (mGPS.canGetLocation) {
				mGPS.getLocation();
			 toPosition = new LatLng(returnLat("lat").get(g), returnLng("lng").get(g));
			 fromPosition = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());
			}
		}*/
		markerOptions = new MarkerOptions();
		 
   		Intent i2 = getIntent();
   		// getting attached intent data
   		String selObj2 = i2.getStringExtra("object");
   		
   		for (int i = 0; i < returnLat("lat").size(); i++) {
   			if (selObj2.equals(new ShowListActivity().returnTitle().get(i))) {
   				
   				selected = i;
   			
   			}}
   			LocationTracker mGPS = new LocationTracker(this);
   			 toPosition = new LatLng(returnLat("lat").get(selected), returnLng("lng").get(selected));
   			 fromPosition = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());
   			
		 GetRouteTask getRoute = new GetRouteTask();
         getRoute.execute();
		
		if (mGPS.canGetLocation) {
			mGPS.getLocation();
			help2 = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());
			//Bitmap iconn = ((BitmapDrawable)showMe("https://cbks1.google.com/cbk?output=thumbnail&cb_client=maps_sv&thumb=2&thumbfov=60&ll="+mGPS.latitude+","+mGPS.longitude+"&yaw=326.1&thumbpegman=1&w=300&h=118")).getBitmap();
			map.addMarker(new MarkerOptions()
					.position(
							new LatLng(mGPS.getLatitude(), mGPS.getLongitude()))
					.snippet(getAddress(mGPS.getLocation()))
					.title("Ваше місцезнаходження"))
					.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
		
		} else {
			Builder builder = new AlertDialog.Builder(this);
			// builder.setIcon(
			// showMe("http://cs14114.vk.me/c312416/v312416926/2992/hOYLReqd9kY.jpg"));
			builder.setTitle("This is a dialog with some simple text...");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					});
		}
		Button location = (Button) findViewById(R.id.button1);
		View.OnClickListener myhandler1 = new View.OnClickListener() {
			public void onClick(View v) {
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(help2, 15));
				map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
			}
		};
		location.setOnClickListener(myhandler1);
		
	}
   
	private class GetRouteTask extends AsyncTask<String, Void, String> {
        
      
        String response = "";
        @Override
        protected void onPreExecute() {
             
        }

        @Override
        protected String doInBackground(String... urls) {
              //Get All Route values
                    document = v2GetRouteDirection.getDocument(fromPosition, toPosition, GMapV2GetRouteDirection.MODE_DRIVING);
                    response = "Success";
              return response;

        }

        @Override
        protected void onPostExecute(String result) {
             // mGoogleMap.clear();
        	
        	
        	
              if(response.equalsIgnoreCase("Success")){
              ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
              PolylineOptions rectLine = new PolylineOptions().width(10).color(
                          Color.RED);

              for (int i = 0; i < directionPoint.size(); i++) {
                    rectLine.add(directionPoint.get(i));
              }
              // Adding route on the map
              map.addPolyline(rectLine);
              markerOptions.position(toPosition);
              markerOptions.draggable(true);
              //map.addMarker(markerOptions);

              }
            
        }
  }
  @Override
  protected void onStop() {
        super.onStop();
        finish();
  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}