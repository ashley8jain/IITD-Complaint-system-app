package com.ashleyjain.iitdcomplaintsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("name"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PostComplaint.class);
                startActivity(intent);
            }
        });

        Bundle bundle = new Bundle();
//      bundle.putString("course_list", courselist_response);
        Complaint_list fragment = new Complaint_list();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                        //.addToBackStack("toMainFragment")
                .commit();

    }

    public void replaceFragment(Fragment courseFrag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, courseFrag, courseFrag.toString())
                .addToBackStack(courseFrag.toString())
                .commit();
    }
}
