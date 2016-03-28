package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {
    protected Drawer drawer = null;
    AccountHeader headerResult= null;
    Context context = MainActivity.this;
    String notJSON;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.notification:
                String url3 = "http://"+LoginActivity.ip+"/first/default/notification.json";
                final ProgressDialog dialog1 = ProgressDialog.show(this, "", "Fetching Details...", true);
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String notResult) {
                        System.out.println(notResult);
                        notJSON = notResult;
                        if (!((getFragmentManager().findFragmentById(R.id.fragment_container)) instanceof NotificationFragment)) {
                            NotificationFragment notifFragment = new NotificationFragment();
                            Bundle bundle = new Bundle();
                            Boolean IsNotif = false;
                            if (notJSON != null) {
                                bundle.putString("notJSON", notJSON);
                                IsNotif = true;
                            }
                            bundle.putBoolean("IsNotif", IsNotif);
                            notifFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, notifFragment, notifFragment.toString())
                                    .addToBackStack(notifFragment.toString())
                                    .commit();
                        }
                    }
                }, this, url3, dialog1);
                return true;
            case R.id.material_drawer_switch:
                drawer.openDrawer();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
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
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                        //.addToBackStack("toMainFragment")
                .commit();

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName("ashley").withEmail("123@gmail.com"),
                        new ProfileSettingDrawerItem()
                                .withName("Log Out")
                                .withIdentifier(1)
                )
                .withHeaderBackground(R.drawable.iit_delhi)
                .withProfileImagesClickable(true)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {

                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {

                        if (profile != null && profile instanceof IDrawerItem) {
                            switch ((int) profile.getIdentifier()) {
//                                case 1:
//                                    Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_LONG).show();
//                                    final ProgressDialog dialog2 = ProgressDialog.show(context, "", "Loading.Please wait...", true);
//
//                                    String url = "http://"+ ((myApplication) getApplication()).getLocalHost() +"/default/logout.json";
//                                    GETrequest.response(new GETrequest.VolleyCallback() {
//                                        @Override
//                                        public void onSuccess(String result) {
//                                            dialog2.dismiss();
//                                            System.out.println(result);
//                                            finish();
//                                        }
//                                    }, context, url, dialog2);
//                                    break;
                                default:
                                    break;
                            }
                        }
                        return false;
                    }
                })

                .build();


        final DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Individual").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Hostel").withIdentifier(2),
                        new PrimaryDrawerItem().withName("Institute").withIdentifier(3));
        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                Bundle courseB = new Bundle();
                switch (position) {
                    default:

                        break;
                }
                return false;
            }
        });
        drawer = builder.build();
//        TextView Name = (TextView) headerResult.getView().findViewById(R.id.material_drawer_account_header_name);
//        TextView Email = (TextView) headerResult.getView().findViewById(R.id.material_drawer_account_header_name);


    }

}
