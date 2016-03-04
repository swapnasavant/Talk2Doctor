package com.healthapp.src;


import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;


public class CommentAdapter extends ArrayAdapter<Comment> {

    int resource;
    String response;
    Context context;
    //Initialize adapter
    public CommentAdapter(Context context, int resource, List<Comment> items) {
        super(context, resource, items);
        this.resource=resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LinearLayout commentView;
        //Get the current alert object
        Comment comment = getItem(position);

        //Inflate the view
        if(convertView==null)
        {
            commentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, commentView, true);
        }
        else
        {
            commentView = (LinearLayout) convertView;
        }
        if (position % 2 == 0) {
            commentView.setBackgroundResource(R.drawable.list_backgroundcolor);
        } else {
            commentView.setBackgroundResource(R.drawable.alternate_list_backgroundcolor);
        }
        //Get the text boxes from the listitem.xml file
        TextView from =(TextView)commentView.findViewById(R.id.txtFrom);
        TextView commentText =(TextView)commentView.findViewById(R.id.txtComment);
        final Button dismiss_button = (Button)commentView.findViewById(R.id.reply);
        LayoutInflater layoutInflater
                = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView1 = layoutInflater.inflate(R.layout.popup, null);
        final Button save_button = (Button)popupView1.findViewById(R.id.save);
        dismiss_button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(dismiss_button, 50, -30);
                popupWindow.setFocusable(true);
                popupWindow.update();

            }});

        save_button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                EditText commentNameET= (EditText)popupView.findViewById(R.id.commentName);

                String commentName = commentNameET.getText().toString();

                // Instantiate Http Request Param Object
                RequestParams params = new RequestParams();
                // When Name Edit View, Email Edit View and Password Edit View have values other than Null
                if(Utility.isNotNull(commentName)){
                    // Put Http parameter name with value of Name Edit View control
                    params.put("commentname", commentName);

                    // Invoke RESTful Web Service with Http parameters
                       // invokeWS(params);
                }
                // When any of the Edit View control left blank
                else{
                    Toast.makeText(popupView.getContext().getApplicationContext(), "Please enter comments", Toast.LENGTH_LONG).show();
                }
            }});
        //Assign the appropriate data from our alert object above
        from.setText(comment.from);
        TextView username_from =(TextView)commentView.findViewById(R.id.txtUserFrom);
        username_from.setText(comment.from.split("@")[0]);
        commentText.setText(comment.commentTxt);

        return commentView;
    }

}


