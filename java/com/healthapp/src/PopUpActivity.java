package com.healthapp.src;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

/**
 * 
 * Home Screen Activity
 */
public class PopUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String username = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			username = extras.getString("USERNAME");
		}
		LayoutInflater layoutInflater
				= (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.popup, null);
		View listitems = layoutInflater.inflate(R.layout.listitems, null);
		final PopupWindow popupWindow = new PopupWindow(
				popupView,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		Button reply_button = (Button)listitems.findViewById(R.id.reply);
		Button btnDismiss = (Button)findViewById(R.id.dismiss);
		final Button save_button = (Button)findViewById(R.id.save);
		popupWindow.showAsDropDown(reply_button, 50, -30);

		btnDismiss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				popupWindow.dismiss();



			}
		});

		save_button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				LayoutInflater layoutInflater
						= (LayoutInflater) getApplicationContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(R.layout.popup, null);
				EditText commentNameET = (EditText) popupView.findViewById(R.id.commentName);

				String commentName = commentNameET.getText().toString();

				// Instantiate Http Request Param Object
				RequestParams params = new RequestParams();
				// When Name Edit View, Email Edit View and Password Edit View have values other than Null
				if (Utility.isNotNull(commentName)) {
					// Put Http parameter name with value of Name Edit View control
					params.put("commentname", commentName);

					// Invoke RESTful Web Service with Http parameters
					// invokeWS(params);
				}
				// When any of the Edit View control left blank
				else {
					Toast.makeText(popupView.getContext().getApplicationContext(), "Please enter comments", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
