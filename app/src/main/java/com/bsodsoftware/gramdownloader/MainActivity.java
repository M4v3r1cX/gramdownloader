package com.bsodsoftware.gramdownloader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bsodsoftware.gramdownloader.services.GramDownloaderService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GramDownloaderService gramDownloaderService;
    private Button btnDownload;
    private EditText txtLink;
    private final int STORAGE_PERMISSION = 2501;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                txtLink.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
            }
        }

        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("GramDownloader needs Storage access permission in order to download videos.\nPlease grant the permission!")
                            .setTitle("Error")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    checkPermissions();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }
    }


    private void init() {
        setServices();
        setControls();
    }

    private void setServices() {
        gramDownloaderService = new GramDownloaderService(this);
    }

    private void setControls() {
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
        txtLink = (EditText) findViewById(R.id.txtLink);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDownload:
                tryDownload();
                break;
        }
    }

    private void tryDownload() {
        try {
            String text = txtLink.getText().toString();
            if (text != null && !text.isEmpty()) {
                Toast.makeText(this, "Trying to Download Video..." , Toast.LENGTH_LONG).show();
                gramDownloaderService.process(text);
            } else {
                Toast.makeText(this, "Error: URL can't be empty!" , Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
