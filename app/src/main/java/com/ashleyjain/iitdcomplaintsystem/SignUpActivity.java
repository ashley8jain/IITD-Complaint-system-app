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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity  {

    EditText firstname,lastname,username,password;
    Button signup;
    String hostel,dept;
    Context context = SignUpActivity.this;
    Integer typee=1;
    RadioGroup radioGroup;

    //Radio button handling
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.instituteadmin:
                if (checked)
                    typee = 3;
                break;
            case R.id.hosteladmin:
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

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.sign_up);
        firstname.setTypeface(font);lastname.setTypeface(font);username.setTypeface(font);signup.setTypeface(font);

        firstname.addTextChangedListener(new checkError(firstname));
        lastname.addTextChangedListener(new checkError(lastname));
        username.addTextChangedListener(new checkError(username));
        password.addTextChangedListener(new checkError(password));

        // Spinner element
        Spinner spinner_hostel = (Spinner) findViewById(R.id.spinnerHostel);
        Spinner spinner_department = (Spinner) findViewById(R.id.spinnerDepartment);
        // Spinner click listener
        spinner_hostel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                hostel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                dept = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> categoriesOfHostel = new ArrayList<String>();
        categoriesOfHostel.add("Vindhyachal");
        categoriesOfHostel.add("Karakoram");
        categoriesOfHostel.add("Shivalik");
        categoriesOfHostel.add("Khailash");
        categoriesOfHostel.add("Kumaon");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterHostel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesOfHostel);

        // Drop down layout style - list view with radio button
        dataAdapterHostel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_hostel.setAdapter(dataAdapterHostel);

        List<String> categoriesOfDept = new ArrayList<String>();
        categoriesOfDept.add("general");
        categoriesOfDept.add("maintenance");
        categoriesOfDept.add("mess");
        categoriesOfDept.add("sports");
        categoriesOfDept.add("cultural");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterDepartment = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesOfDept);

        // Drop down layout style - list view with radio button
        dataAdapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_department.setAdapter(dataAdapterDepartment);





        final String[] fn = new String[1];final String[] ln = new String[1]; final String[] un = new String[1]; final String[] pw = new String[1];

        //register button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fn[0] = firstname.getText().toString();
                ln[0] = lastname.getText().toString();
                un[0] = username.getText().toString();
                pw[0] = password.getText().toString();

                final ProgressDialog dialog = ProgressDialog.show(context, "", "Signing Up...", true);
                String url = "http://"+ LoginActivity.ip +"/first/default/register.json?first_name="+fn[0]+"&last_name="+ln[0]+"&hostel_name="+hostel+"&department="+dept+"&type_="+typee+"&password=i"+pw[0]+"&username="+un[0];

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

}
