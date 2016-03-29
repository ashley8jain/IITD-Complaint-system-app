package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ashleyjain.iitdcomplaintsystem.functions.GETrequest;
import com.ashleyjain.iitdcomplaintsystem.functions.checkError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostComplaint extends AppCompatActivity implements OnItemSelectedListener {

    EditText title,description;
    Button submit;
    String newtitle,newdesc;
    Integer level = 3;
    Integer deptint = 1;
    String department;

    final Context context = PostComplaint.this;

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.institute:
                if (checked)
                    level = 3;
                    break;
            case R.id.hostel:
                if (checked)
                    level = 2;
                    break;
            case R.id.individual:
                if (checked)
                    level = 1;
                    break;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        department = parent.getItemAtPosition(position).toString();
        switch(department){
            case "General":deptint = 1;
                            break;
            case "Mess":deptint = 3;
                            break;
            case "Sports":deptint = 4;
                            break;
            case "Cultural":deptint = 5;
                            break;
            case "Maintenance":deptint =2;
                            break;
            default:deptint = 1;
                    break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface font = Typeface.createFromAsset(getAssets(),"YuppySC-Regular.ttf");


        title = (EditText) findViewById(R.id.title1);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);

        title.addTextChangedListener(new checkError(title));
        title.setTypeface(font);

        description.addTextChangedListener(new checkError(description));
        description.setTypeface(font);


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("General");
        categories.add("Mess");
        categories.add("Sports");
        categories.add("Cultural");
        categories.add("Maintenance");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        submit.setTypeface(font);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtitle = title.getText().toString();
                newdesc = description.getText().toString();

                newtitle = newtitle.replace(' ','+');
                newdesc = newdesc.replace(' ','+');
                final ProgressDialog dialog = ProgressDialog.show(context, "", "Posting Complaint...", true);

                String url = "http://"+ LoginActivity.ip +"/first/complaint/new.json?title="+newtitle+"&description="+newdesc+"&level="+level+"&complaint_dept="+deptint;

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
