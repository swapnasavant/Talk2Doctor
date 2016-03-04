package com.healthapp.src;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Home Screen Activity
 */
public class HomeActivity extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String username = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			username = extras.getString("USERNAME");
		}
		listView = (ListView) findViewById(R.id.commentList);



		List<Comment> commentList = new ArrayList<Comment>();
		CommentAdapter adapter = new CommentAdapter(this, R.layout.listitems, commentList);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// Defined Array values to show in ListView
		invokeWS(username, adapter);


	}

	public void invokeWS(String email, final CommentAdapter adapter){

		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();

		client.get("http://ec2-52-36-9-143.us-west-2.compute.amazonaws.com/HealthApp-0.0.1-SNAPSHOT/healthapp/healthappservice/comments/" + email, new AsyncHttpResponseHandler() {
			// When the response returned by REST has Http response code '200'
			@Override
			public void onSuccess(String response) {

				try {
					// JSON Object
					JSONArray obj = new JSONArray(response);
					for (int i=0; i< obj.length(); i++) {
						JSONObject actor = obj.getJSONObject(i);
						adapter.add(new Comment(actor.getString("from_user"), actor.getString("comments")));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
					e.printStackTrace();

				}
			}

			// When the response returned by REST has Http response code other than '200'
			@Override
			public void onFailure(int statusCode, Throwable error,
								  String content) {

				// When Http response code is '404'
				if (statusCode == 404) {
					Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
				}
				// When Http response code is '500'
				else if (statusCode == 500) {
					Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
				}
				// When Http response code other than 404, 500
				else {
					Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
				}


			}
		});


	}



}
