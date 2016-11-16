package com.jeffrkarmy.sheetmetalreference;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jeffrkarmy.sheetmetalreference.Class.HistoryDB;
import com.jeffrkarmy.sheetmetalreference.Class.HistoryDbHelper;
import com.jeffrkarmy.sheetmetalreference.Class.MySharedPreferences;
import com.jeffrkarmy.sheetmetalreference.Class.customAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static HistoryDbHelper historyDbHelper;
    private ListView listView;
    private MySharedPreferences preferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        preferences = new MySharedPreferences();
        if (preferences.getBooleanPreference(this, getString(R.string.pref_file_name), getString(R.string.pref_warning))) {
            preferences.setBooleanPreference(this, getString(R.string.pref_file_name), getString(R.string.pref_warning), false);
            Intent intent = new Intent(this, WarningActivity.class);
            startActivity(intent);
        }

        historyDbHelper = new HistoryDbHelper(this);

        if (historyDbHelper.hasEntries()) {
            Spinner spinner;
            ArrayAdapter<CharSequence> adapter;

            listView = (ListView) findViewById(R.id.list);
            spinner = (Spinner) findViewById(R.id.spinner);
            adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    HistoryDB[] list;
                    switch (position) {
                        case 0:
                            list = historyDbHelper.getAllEntriesArrayByMT();
                            if (list != null)
                                listView.setAdapter(new customAdapter(parentView.getContext(), list));

                            break;
                        case 1:
                            list = historyDbHelper.getAllEntriesArrayByRadius();
                            if (list != null)
                                listView.setAdapter(new customAdapter(parentView.getContext(), list));

                            break;
                        case 2:
                            list = historyDbHelper.getAllEntriesArrayByAngle();
                            if (list != null)
                                listView.setAdapter(new customAdapter(parentView.getContext(), list));

                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long ident) {

                    final View myView = view;

                    new AlertDialog.Builder(view.getContext())
                            .setTitle(getString(R.string.app_name))
                            .setMessage(getString(R.string.delete_row))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    TextView materialThickness = (TextView) myView.findViewById(R.id.textViewMT);
                                    TextView radius = (TextView) myView.findViewById(R.id.textViewRadius);
                                    TextView angle = (TextView) myView.findViewById(R.id.textViewAngle);

                                    double mt = Double.parseDouble(materialThickness.getText().toString());
                                    double r = Double.parseDouble(radius.getText().toString());
                                    double a = Double.parseDouble(angle.getText().toString());

                                    int success = historyDbHelper.deleteEntries(mt, r, a);

                                    if (success > 0) {
                                        historyDbHelper = new HistoryDbHelper(myView.getContext());

                                        if (historyDbHelper.hasEntries()) {
                                            listView = (ListView) findViewById(R.id.list);
                                            listView.setAdapter(new customAdapter(myView.getContext(), historyDbHelper.getAllEntriesArray()));
                                        } else {
                                            RelativeLayout containerMain = (RelativeLayout) findViewById(R.id.containerMainEntryList);
                                            RelativeLayout containerLogo = (RelativeLayout) findViewById(R.id.containerBigSMMLogo);
                                            containerMain.setVisibility(View.GONE);
                                            containerLogo.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }

                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }

            });
        } else {
            RelativeLayout containerMain = (RelativeLayout) findViewById(R.id.containerMainEntryList);
            RelativeLayout containerLogo = (RelativeLayout) findViewById(R.id.containerBigSMMLogo);
            containerMain.setVisibility(View.GONE);
            containerLogo.setVisibility(View.VISIBLE);
        }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
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
            Intent intent = new Intent(this, MaterialMinimumBendRadius.class);
            startActivity(intent);
        } else if (id == R.id.activity_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.main_activity) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
