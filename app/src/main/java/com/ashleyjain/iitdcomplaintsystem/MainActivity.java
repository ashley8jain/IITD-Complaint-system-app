package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondarySwitchDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class MainActivity extends AppCompatActivity  {
    protected Drawer drawer = null;
    AccountHeader headerResult= null;
    Context context = MainActivity.this;
    String notJSON;
    public static Integer filter = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

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
                        if (!((getFragmentManager().findFragmentById(R.id.pager)) instanceof NotificationFragment)) {
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
                                    .replace(R.id.pager, notifFragment, notifFragment.toString())
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
                Intent intent = new Intent(MainActivity.this, PostComplaint.class);
                startActivity(intent);
            }
        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Individual"));
        tabLayout.addTab(tabLayout.newTab().setText("Hostel"));
        tabLayout.addTab(tabLayout.newTab().setText("Institute"));



        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                    viewPager.invalidate();
                    adapter.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
        final DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withHasStableIds(true)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Filter"),
                        new SecondaryDrawerItem().withName("General").withIdentifier(4),
                        new SecondaryDrawerItem().withName("Maintenance").withIdentifier(5),
                        new SecondaryDrawerItem().withName("Mess").withIdentifier(6),
                        new SecondaryDrawerItem().withName("Sports").withIdentifier(7),
                        new SecondaryDrawerItem().withName("Cultural").withIdentifier(8)
                );


        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                Bundle courseB = new Bundle();
                switch (position) {
                    default:

                        if(position == 2){
                                filter =1;
                            viewPager.invalidate();
                        }
                        if(position == 3){
                             filter =2;
                             viewPager.invalidate();
                        }
                        if(position == 4){
                             filter =3;
                             viewPager.invalidate();
                        }
                        if(position == 5){
                             filter =4;
                             viewPager.invalidate();
                        }
                        if(position == 6){
                             filter =5;
                             viewPager.invalidate();
                        }


                        break;
                }
                return false;
            }
        });
        drawer = builder.build();


    }

}
