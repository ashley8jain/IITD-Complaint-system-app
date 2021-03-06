package com.ashleyjain.iitdcomplaintsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SpecificComplaint extends AppCompatActivity {

    String current_user_id;
    Integer cId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cId = getIntent().getIntExtra("id",1);
        current_user_id = getIntent().getStringExtra("current_user_id");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecificComplaint.this, PostComplaint.class);
                startActivity(intent);
            }
        });

        //transferring complaint id
        Bundle bundle = new Bundle();
        bundle.putInt("id",cId);
        Specific_complaint fragment = new Specific_complaint();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }



}
