package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText firstname,lastname,username,department,password;
    Button signup;
    String hostel;
    Context context = SignUpActivity.this;
    Integer typee=1;

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.admin:
                if (checked)
                    typee = 2;
                break;
            case R.id.normal:
                if (checked)
                    typee = 1;
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(),"YuppySC-Regular.ttf");


        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        department = (EditText) findViewById(R.id.department);
        password = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.sign_up);
        firstname.setTypeface(font);lastname.setTypeface(font);username.setTypeface(font);department.setTypeface(font);signup.setTypeface(font);

        firstname.addTextChangedListener(new checkError(firstname));
        lastname.addTextChangedListener(new checkError(lastname));
        username.addTextChangedListener(new checkError(username));
        department.addTextChangedListener(new checkError(department));
        password.addTextChangedListener(new checkError(password));

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinnerHostel);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Vindhyachal");
        categories.add("Karakoram");
        categories.add("Shivalik");
        categories.add("Khailash");
        categories.add("Kumaon");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        final String[] fn = new String[1];final String[] ln = new String[1]; final String[] un = new String[1]; final String[] dp = new String[1];  final String[] pw = new String[1];

        //register button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fn[0] = firstname.getText().toString();
                ln[0] = lastname.getText().toString();
                un[0] = username.getText().toString();
                dp[0] = department.getText().toString();
                pw[0] = password.getText().toString();

                final ProgressDialog dialog = ProgressDialog.show(context, "", "Signing Up...", true);
                String url = "http://"+ LoginActivity.ip +"/first/default/register.json?first_name="+fn[0]+"&last_name="+ln[0]+"&hostel_name="+hostel+"&department="+dp[0]+"&type_="+typee+"&password=i"+pw[0]+"&username="+un[0];
                //GET request through stringrequest
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success=="true"){
                                Toast.makeText(getApplicationContext(), "Registered!!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, context, url, dialog);


            }
        });






    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        hostel = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + department, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
