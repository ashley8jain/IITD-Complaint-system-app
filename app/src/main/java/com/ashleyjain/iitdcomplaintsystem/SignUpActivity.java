package com.ashleyjain.iitdcomplaintsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText firstname,lastname,username,hostelname,department,type,password,confirmpassword;
    Button signup;

    Context context = SignUpActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        hostelname = (EditText) findViewById(R.id.hostelname);
        department = (EditText) findViewById(R.id.department);
        type = (EditText) findViewById(R.id.type);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        signup = (Button) findViewById(R.id.sign_up);

        firstname.addTextChangedListener(new checkError(firstname));
        lastname.addTextChangedListener(new checkError(lastname));
        username.addTextChangedListener(new checkError(username));
        hostelname.addTextChangedListener(new checkError(hostelname));
        department.addTextChangedListener(new checkError(department));
        type.addTextChangedListener(new checkError(type));
        password.addTextChangedListener(new checkError(password));
        confirmpassword.addTextChangedListener(new checkError(confirmpassword));



        final String[] fn = new String[1];final String[] ln = new String[1]; final String[] un = new String[1]; final String[] hn = new String[1]; final String[] dp = new String[1]; final String[] tp = new String[1]; final String[] pw = new String[1]; final String[] cnpw = new String[1];
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fn[0] = firstname.getText().toString();
                ln[0] = lastname.getText().toString();
                un[0] = username.getText().toString();
                hn[0] = hostelname.getText().toString();
                dp[0] = department.getText().toString();
                tp[0] = type.getText().toString();
                pw[0] = password.getText().toString();
                cnpw[0] = confirmpassword.getText().toString();

//                final ProgressDialog dialog = ProgressDialog.show(context, "", "Signing Up...", true);
//                String url = "http://"+ LoginActivity.ip +"/first/signup.json?";
//                //GET request through stringrequest
//                GETrequest.response(new GETrequest.VolleyCallback() {
//                    @Override
//                    public void onSuccess(String result) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(result);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, context, url, dialog);


            }
        });






    }

}
