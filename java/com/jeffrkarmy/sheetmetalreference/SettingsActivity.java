package com.jeffrkarmy.sheetmetalreference;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jeffrkarmy.sheetmetalreference.Class.HistoryDbHelper;


public class SettingsActivity extends AppCompatActivity {

    private HistoryDbHelper historyDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        historyDbHelper = new HistoryDbHelper(this);
        TextView textViewAppVersion = (TextView) findViewById(R.id.textViewSettingsAppVersion);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            textViewAppVersion.setText(pInfo.versionName);
        } catch (Exception ex) {

        }
    }

    public void onClickClearData(View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.settings_warning_delete))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        historyDbHelper.deleteAllHistoryDBEntries();
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

    public void onClickSendEmail(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_email)});
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
            //intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.smm_awesome));
            startActivity(Intent.createChooser(intent, getString(R.string.send_mail)));
        } catch (Exception ex) {
            Toast.makeText(this, getString(R.string.no_email_client_installed), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCLickShowEULA(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.end_user_license_agreement))
                .setMessage(getString(R.string.eula))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }

                })
                .show();
    }
}
