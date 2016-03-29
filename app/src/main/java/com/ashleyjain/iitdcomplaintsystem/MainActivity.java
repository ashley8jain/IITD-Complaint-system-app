package com.ashleyjain.iitdcomplaintsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class MainActivity extends AppCompatActivity  {

    protected Drawer drawer = null;
    AccountHeader headerResult= null;
    Context context = MainActivity.this;
    String notJSON;
    String fullname,username;
    public static Integer filter = 0;

    boolean doubleBackToExitPressedOnce = false;
    FragmentManager fm;

    //automatically refreshing page when creating complaint and selecting filter
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(MainActivity.this, MainActivity.class);  //your class
        startActivity(i);
        finish();

    }

    //Back Button handling
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
            alertbuilder.setTitle("Do you mean to exit or logout?");
            alertbuilder.setCancelable(true);
            alertbuilder.setNegativeButton("LogOut",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            alertbuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                }
            });
            AlertDialog alertDialog = alertbuilder.create();
            alertDialog.show();
            return;
        }
        fm.popBackStack();
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //toolbar button handling
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.notification:
                String url3 = "http://"+LoginActivity.ip+"/first/default/notification.json";
                final ProgressDialog dialog1 = ProgressDialog.show(this, "", "Fetching Details...", true);
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String notResult) {
                        System.out.println(notResult);
                        notJSON = notResult;
                        if (!((getSupportFragmentManager().findFragmentById(R.id.fragment_not)) instanceof NotificationFragment)) {
                            NotificationFragment notifFragment = new NotificationFragment();
                            Bundle bundle = new Bundle();
                            Boolean IsNotif = false;
                            if (notJSON != null) {
                                bundle.putString("notJSON", notJSON);
                                IsNotif = true;
                            }
                            bundle.putBoolean("IsNotif", IsNotif);
                            notifFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_not, notifFragment, notifFragment.toString())
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
        fm = getSupportFragmentManager();
        Intent intent = getIntent();
        fullname = intent.getStringExtra("name");
        username = intent.getStringExtra("username");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostComplaint.class);
                startActivity(intent);
            }
        });

        ViewpagerFragment fragment = new ViewpagerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_not,fragment,fragment.toString())
                .addToBackStack(fragment.toString())
                .commit();

        //profile section in drawer layout
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(fullname).withEmail(username),
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
                                case 1:
                                    Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_LONG).show();
                                    finish();
                                    break;
                                default:
                                    break;
                            }
                        }
                        return false;
                    }
                })
                .build();


        OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if (drawerItem instanceof Nameable) {
                    Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);

                } else {
                    Log.i("material-drawer", "toggleChecked: " + isChecked);
                }
            }
        };

        //buttons handling in drawer
        final DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withHasStableIds(true)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Filter"),
                        new PrimaryDrawerItem().withName("General").withIdentifier(4).withIcon(R.drawable.ic_home_black_18dp),
                        new PrimaryDrawerItem().withName("Maintenance").withIdentifier(5).withIcon(R.drawable.wrench),
                        new PrimaryDrawerItem().withName("Mess").withIdentifier(6).withIcon(R.drawable.food),
                        new PrimaryDrawerItem().withName("Sports").withIdentifier(7).withIcon(R.drawable.wrench),
                        new PrimaryDrawerItem().withName("Cultural").withIdentifier(8).withIcon(R.drawable.ic_event_black_18dp)
                );


        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                Bundle courseB = new Bundle();
                switch (position) {
                    default:

                        if(position == 2){
                                filter =1;
                            //viewPager.invalidate();
                        }
                        if(position == 3){
                             filter =2;
                             //viewPager.invalidate();
                        }
                        if(position == 4){
                             filter =3;
                             //viewPager.invalidate();
                        }
                        if(position == 5){
                             filter =4;
                             //viewPager.invalidate();
                        }
                        if(position == 6){
                             filter =5;
                             //viewPager.invalidate();
                        }
                        Intent i = new Intent(MainActivity.this, MainActivity.class);  //your class
                        startActivity(i);
                        finish();
                        break;
                }

                return false;
            }
        });
        drawer = builder.build();


    }

}
