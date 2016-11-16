package com.jeffrkarmy.sheetmetalreference;

import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeffrkarmy.sheetmetalreference.Class.Calculator;
import com.jeffrkarmy.sheetmetalreference.Class.HistoryDB;
import com.jeffrkarmy.sheetmetalreference.Class.HistoryDbHelper;
import com.jeffrkarmy.sheetmetalreference.Class.Utility;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AngleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Utility utility;
    private Calculator cal;
    private InputMethodManager imm;
    private EditText materialThickness;
    private EditText radius;
    private EditText angle;
    private EditText legA;
    private EditText legB;
    private TextView textViewFlatPattern;
    private RelativeLayout containerAngle;
    private RelativeLayout containerFlatPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angle_bend);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Suppress the keyboard on page load.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        utility = new Utility();
        cal = new Calculator();

        materialThickness = (EditText) findViewById(R.id.editTextMT);
        radius = (EditText) findViewById(R.id.editTextRadius);
        angle = (EditText) findViewById(R.id.editTextAngle);
        legA = (EditText) findViewById(R.id.editTextLegA);
        legB = (EditText) findViewById(R.id.editTextLegB);

        textViewFlatPattern = (TextView) findViewById(R.id.editTextAngleOutPut);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        } else if (id == R.id.minimum_cold_bend) {
            Intent intent = new Intent(this, MaterialMinimumBendRadius.class);
            startActivity(intent);
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

    /**
     * Click handler for calculate.
     *
     * @param view
     */
    public void onClickCalculate(View view) //
    {
        //Check if EditText box has a entry.
        if (materialThickness.getText().toString().trim().length() == 0) {
            materialThickness.requestFocus();
            imm.showSoftInput(materialThickness, InputMethodManager.SHOW_IMPLICIT);
        } else if (radius.getText().toString().trim().length() == 0) {
            radius.requestFocus();
            imm.showSoftInput(radius, InputMethodManager.SHOW_IMPLICIT);
        } else if (angle.getText().toString().trim().length() == 0) {
            angle.requestFocus();
            imm.showSoftInput(angle, InputMethodManager.SHOW_IMPLICIT);
        } else if ((legA.getText().toString().trim().length() == 0)) {
            legA.requestFocus();
            imm.showSoftInput(legA, InputMethodManager.SHOW_IMPLICIT);
        } else if ((legB.getText().toString().trim().length() == 0)) {
            legB.requestFocus();
            imm.showSoftInput(legB, InputMethodManager.SHOW_IMPLICIT);
        } else {
            double mt = Double.parseDouble(materialThickness.getText().toString());
            double r = Double.parseDouble(radius.getText().toString());
            double a = Double.parseDouble(angle.getText().toString());
            double leg_A = Double.parseDouble(legA.getText().toString());
            double leg_B = Double.parseDouble(legB.getText().toString());

            if (cal.greaterThanZero(mt)) {
                utility.alertMessage(this, getString(R.string.greater_than_zero), getString(R.string.ok));
                materialThickness.requestFocus();
                imm.showSoftInput(materialThickness, InputMethodManager.SHOW_IMPLICIT);
            } else if (cal.greaterThanZero(r)) {
                utility.alertMessage(this, getString(R.string.greater_than_zero), getString(R.string.ok));
                radius.requestFocus();
                imm.showSoftInput(radius, InputMethodManager.SHOW_IMPLICIT);
            } else if (cal.checkAngle(a)) {
                utility.alertMessage(this, getString(R.string.degrees_greater_zero_less_one_eighty), getString(R.string.ok));
                angle.requestFocus();
                imm.showSoftInput(angle, InputMethodManager.SHOW_IMPLICIT);
            } else if (cal.greaterThanZero(leg_A)) {
                utility.alertMessage(this, getString(R.string.greater_than_zero), getString(R.string.ok));
                legA.requestFocus();
                imm.showSoftInput(legA, InputMethodManager.SHOW_IMPLICIT);
            } else if (cal.greaterThanZero(leg_B)) {
                utility.alertMessage(this, getString(R.string.greater_than_zero), getString(R.string.ok));
                legB.requestFocus();
                imm.showSoftInput(legB, InputMethodManager.SHOW_IMPLICIT);
            } else {
                double ba = cal.empiricalBendAllowanceFormula(mt, r, a);
                double bd = cal.bendDeduction(ba, r, mt, a);

                double flatA = cal.calculateFlat(bd, leg_A);
                double flatB = cal.calculateFlat(bd, leg_B);
                double blank = flatA + flatB;

                HistoryDB historyDB = new HistoryDB(mt, r, a, bd);
                HistoryDbHelper historyDbHelper = new HistoryDbHelper(this);
                historyDbHelper.addEntry(historyDB);

                DecimalFormat df = new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.CEILING);

                containerAngle = (RelativeLayout) findViewById(R.id.containerAngle);
                containerFlatPattern = (RelativeLayout) findViewById(R.id.containerFlatPattern);

                containerAngle.setVisibility(View.GONE);
                containerFlatPattern.setVisibility(View.VISIBLE);

                String out = getString(R.string.flat_a) + " " + df.format(flatA) + "\n" + getString(R.string.flat_b) + " " + df.format(flatB) + "\n" + getString(R.string.total_blank_size) + " " + df.format(blank);

                textViewFlatPattern.setText(out);

                // Check if no view has focus:
                view = this.getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }

    public void onClickWaringAngleBend(View view) {
        Intent intent = new Intent(this, WarningActivity.class);
        startActivity(intent);
    }
}
