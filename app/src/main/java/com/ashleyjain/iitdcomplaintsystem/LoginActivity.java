package com.ashleyjain.iitdcomplaintsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText id,pass;
    Button login;
    TextView signup;

    private static LoginActivity _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public static LoginActivity get() {
        return _instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Context context = LoginActivity.this;

        id = (EditText) findViewById(R.id.userid);
        pass = (EditText) findViewById(R.id.password);
        final String[] username = new String[1];
        final String[] password = new String[1];


        login = (Button) findViewById(R.id.button);
        signup = (TextView) findViewById(R.id.textView);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected(context)) {
                    AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
                    alertbuilder.setTitle("No Network Connection");
                    alertbuilder.setCancelable(true);
                    alertbuilder.setPositiveButton("Go to wifi settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = alertbuilder.create();
                    alertDialog.show();
                } else {
                    final ProgressDialog dialog = ProgressDialog.show(context,"", "Authenticating...", true);
                    username[0] = id.getText().toString();
                    password[0] = pass.getText().toString();

                    String url = "http://10.192.42.105:8000/default/login.json?userid=" + username[0] + "&password=" + password[0];

                    //GET request through stringrequest
                    GETrequest.response(new GETrequest.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {

                                JSONObject jsonObject = new JSONObject(result);
                                String success = jsonObject.getString("success");
                                if (success == "false") {
                                    //user inputs are wrong
                                    Toast.makeText(context, "Wrong username or password!!", Toast.LENGTH_LONG).show();
                                } else {
                                    final JSONObject user = jsonObject.getJSONObject("user");
                                    final String fname = user.getString("first_name");
                                    final String lname = user.getString("last_name");
                                    final Intent main2frag_intent = new Intent(context, MainActivity.class);
                                    main2frag_intent.putExtra("name", fname+" "+lname);
                                    main2frag_intent.putExtra("username", username);
                                    startActivity(main2frag_intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, context, url, dialog);


                }
            }
        });
    }

        //Check if there is network connection or not
    public static boolean isNetworkConnected(Context con) {
        ConnectivityManager connMgr = (ConnectivityManager) con.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    //Volley request queue
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }
}
