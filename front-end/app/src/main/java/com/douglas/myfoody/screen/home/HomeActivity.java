package com.douglas.myfoody.screen.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.utilities.Utils;
import com.douglas.myfoody.screen.main.MainActivity;
import com.douglas.myfoody.screen.promotion.InviteFriendFragment;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static FragmentManager fragmentManager;
    private UserViewModel mUserViewModel;
    private int menuToChoose = R.menu.home;
    private static NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        System.out.println("HERE");
        // create Fragment
        fragmentManager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add explore restaurant
        if(savedInstanceState == null){
            navigationView.setCheckedItem(R.id.nav_explore_restaurant);
            onNavigationItemSelected(navigationView.getCheckedItem());
        }

        // get user info and store in UserViewModel
        Intent intent = getIntent();
        if (intent != null) {
            User parcelableUser = intent.getParcelableExtra("user");

            if(parcelableUser == null) {
                parcelableUser = Utils.getLoggedInUser();
            }

            mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            mUserViewModel.setUser(parcelableUser);

            //update username and user avatar as the first letter of username
            View headerView = navigationView.getHeaderView(0);
            TextView avatar = headerView.findViewById(R.id.avatar);
            avatar.setText(parcelableUser.getFullName().toUpperCase().charAt(0) + "");
            TextView navUsername = (TextView) headerView.findViewById(R.id.username);
            navUsername.setText(parcelableUser.getFullName());

            // Set user object to static class
            Utils.setLoggedInUser(parcelableUser);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //check current fragment is explore_restaurant
            ExploreRestaurantFragment exploreFragment
                    = (ExploreRestaurantFragment)fragmentManager.findFragmentByTag("Explore_Restaurant_Fragment");
            if (exploreFragment != null && exploreFragment.isVisible()) {//yes, ask for exit app
                //confirm to exit app
                new AlertDialog.Builder(this)
                        .setTitle("Do you want to exit MyFoody?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                HomeActivity.this.finish();
                                System.exit(0);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }else{//no, navigate to the explore restaurant fragment
                navigationView.setCheckedItem(R.id.nav_explore_restaurant);
                onNavigationItemSelected(navigationView.getCheckedItem());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuToChoose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore_restaurant) {
            setTitle("Explore Restaurant");
            menuToChoose = R.menu.home;
            fragmentManager.beginTransaction()
                    .replace(R.id.homeFrameContainer, new ExploreRestaurantFragment(),
                            "Explore_Restaurant_Fragment").commit();

        } else if (id == R.id.nav_user_info) {
            setTitle("User Info");//change title
            menuToChoose = R.menu.user_info_menu;//change menu

            fragmentManager.beginTransaction()
                    .replace(R.id.homeFrameContainer, new UserInfoFragment(),
                            "User_Info_Fragment").commit();

        } else if (id == R.id.nav_my_orders) {


        } else if (id == R.id.nav_invite_friend) {
            fragmentManager.beginTransaction()
                    .replace(R.id.homeFrameContainer, new InviteFriendFragment(),
                            "Invite_Friend_Fragment").commit();

        } else if (id == R.id.nav_change_password) {
            setTitle("Change Password");//change title
            menuToChoose = R.menu.user_info_menu;//change menu

            fragmentManager.beginTransaction()
                    .replace(R.id.homeFrameContainer, new ChangePasswordFragment(),
                            "Change_Password_Fragment").commit();

        } else if (id == R.id.nav_logout) {
            mUserViewModel.setUser(new User());//delete User object
            Utils.setLoggedInUser(new User());
            startActivity(new Intent(this, MainActivity.class));//navigate to login
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
