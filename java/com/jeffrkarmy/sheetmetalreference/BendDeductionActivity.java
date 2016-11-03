package com.jeffrkarmy.sheetmetalreference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jeffrkarmy.sheetmetalreference.Class.Calculator;
import com.jeffrkarmy.sheetmetalreference.Class.HistoryDB;
import com.jeffrkarmy.sheetmetalreference.Class.HistoryDbHelper;
import com.jeffrkarmy.sheetmetalreference.Class.Utility;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BendDeductionActivity extends AppCompatActivity {

    private static Calculator cal;
    private static Utility utility;

    private EditText materialThickness;
    private EditText radius;
    private TextView angle;
    private TextView BD;
    private TextView HalfBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bend_deduction);

        //Suppress the keyboard on page load.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        cal = new Calculator();
        utility = new Utility();

        materialThickness = (EditText) findViewById(R.id.editTextMT);
        radius = (EditText) findViewById(R.id.editTextRadius);
        angle = (EditText) findViewById(R.id.editTextAngle);
        BD = (TextView) findViewById(R.id.textViewBD);
        HalfBD = (TextView) findViewById(R.id.textViewHalfBD);
    }

    /**
     * @param view
     */
    public void calculate(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Check if EditText box has a entry.
        if (materialThickness.getText().toString().trim().length() == 0) {
            materialThickness.requestFocus();
            imm.showSoftInput(materialThickness, InputMethodManager.SHOW_IMPLICIT);
        } else if (radius.getText().toString().trim().length() == 0) {
            radius.requestFocus();
            imm.showSoftInput(radius, InputMethodManager.SHOW_IMPLICIT);
        } else if (angle.getText().toString().trim().length() == 0) {
            //Focus on empty editText box.
            angle.requestFocus();
            imm.showSoftInput(angle, InputMethodManager.SHOW_IMPLICIT);
        } else {
            double mt = Double.parseDouble(materialThickness.getText().toString());
            double r = Double.parseDouble(radius.getText().toString());
            double a = Double.parseDouble(angle.getText().toString());

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
            } else {
                double ba = cal.empiricalBendAllowanceFormula(mt, r, a);
                double deduction = cal.bendDeduction(ba, r, mt, a);

                HistoryDB historyDB = new HistoryDB(mt, r, a, deduction);
                HistoryDbHelper historyDbHelper = new HistoryDbHelper(this);
                historyDbHelper.addEntry(historyDB);

                DecimalFormat df = new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.CEILING);

                String outBD = getString(R.string.bend_deduction) + " " + df.format(deduction);
                String outHalfBD = getString(R.string.half_deduction) + " " + df.format(deduction / 2);

                BD.setText(outBD);
                HalfBD.setText(outHalfBD);

                // Check if no view has focus:
                view = this.getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }

    }

    /**
     * @param view
     */
    public void onClickWaringBendDeductionBend(View view) {
        Intent intent = new Intent(this, WarningActivity.class);
        startActivity(intent);
    }
}
