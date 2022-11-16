package com.anvaishy.easytmedc_receptionist_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class SignedInActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView nav;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        drawer = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.navView);

        setupDrawerContent(nav);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        drawer.addDrawerListener(toggle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_content, new DoctorListFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(toggle.onOptionsItemSelected(item)) return true;

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_med_docs:
                                fragment = new MedDocsFragment();
                                break;
                            case R.id.nav_sos_reqs:
                                fragment = new SOSFragment();
                                break;
                            case R.id.nav_medpass_req:
                                fragment = new MedPassFragment();
                                break;
                            default:
                                fragment = new DoctorListFragment();
                        }

                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.main_content, fragment)
                                .addToBackStack(null)
                                .commit();

                        // Close the navigation drawer
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }
}