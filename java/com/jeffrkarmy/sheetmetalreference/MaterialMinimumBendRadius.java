package com.jeffrkarmy.sheetmetalreference;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.jeffrkarmy.sheetmetalreference.Class.MinimumAluminumBendRadiiDBHelper;
import com.jeffrkarmy.sheetmetalreference.Class.customAdapterAlloy;

import java.io.IOException;

public class MaterialMinimumBendRadius extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private MinimumAluminumBendRadiiDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_minimum_bend_radius);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        spinner = (Spinner) findViewById(R.id.spinnerAluminumAlloy);
        adapter = ArrayAdapter.createFromResource(this, R.array.spinner_aluminum_alloy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                listView = (ListView) findViewById(R.id.listViewAlloy);
                dbHelper = new MinimumAluminumBendRadiiDBHelper(parentView.getContext());

                try {
                    dbHelper.createDataBase();//TODO: THIS EXCEPTION NEEDS WORK.
                } catch (IOException io) {

                }

                switch (position) {
                    case 0://1100
                        listView.setAdapter(new customAdapterAlloy(parentView.getContext(), dbHelper.getAllEntriesArray(1100)));
                        break;
                    case 1://2014
                        listView.setAdapter(new customAdapterAlloy(parentView.getContext(), dbHelper.getAllEntriesArray(2014)));
                        break;
                    case 2://2024
                        listView.setAdapter(new customAdapterAlloy(parentView.getContext(), dbHelper.getAllEntriesArray(2024)));
                        break;
                    case 3://3003
                        listView.setAdapter(new customAdapterAlloy(parentView.getContext(), dbHelper.getAllEntriesArray(3003)));
                        break;
                    case 4://5052
                        listView.setAdapter(new customAdapterAlloy(parentView.getContext(), dbHelper.getAllEntriesArray(5052)));
                        break;
                    case 5://6061
                        listView.setAdapter(new customAdapterAlloy(parentView.getContext(), dbHelper.getAllEntriesArray(6061)));
                        break;
                    case 6://7075
                        listView.setAdapter(new customAdapterAlloy(parentView.getContext(), dbHelper.getAllEntriesArray(7075)));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.bend_deduction_activity) {
            Intent intent = new Intent(this, BendDeductionActivity.class);
            startActivity(intent);
        } else if (id == R.id.angle_bend) {
            Intent intent = new Intent(this, AngleActivity.class);
            startActivity(intent);
        } else if (id == R.id.minimum_cold_bend) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        } else if (id == R.id.activity_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.main_activity) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
