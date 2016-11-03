package com.jeffrkarmy.sheetmetalreference;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WarningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
    }

    public void onClickDismiss(View view) {
        finish();
    }
}
