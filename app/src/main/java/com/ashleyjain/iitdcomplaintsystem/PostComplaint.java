package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PostComplaint extends AppCompatActivity {

    EditText title,description;
    Button submit;
    String newtitle,newdesc;

    final Context context = PostComplaint.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_complaint);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.title1);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtitle = title.getText().toString();
                newdesc = description.getText().toString();

                final ProgressDialog dialog = ProgressDialog.show(context, "", "Authenticating...", true);
                String url = "http://"+ LoginActivity.ip +"/first/complaint/new.json?title="+newtitle+"&description="+newdesc;

                //GET request through stringrequest
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            String success = jsonObject.getString("success");
                            if (success == "false") {
                                //user inputs are wrong
                                Toast.makeText(context, "Could not post complaint!!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Complaint Posted!!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, context, url, dialog);

            }
        });

    }

}
