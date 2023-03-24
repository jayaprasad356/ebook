package com.greymatterworks.ebook.activitys;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.greymatterworks.ebook.R;

public class PublishActivity extends AppCompatActivity {
    RelativeLayout rlUploadPdf;

    private static final int REQUEST_CODE_PICK_PDF_FILE = 1;
    private static final int REQUEST_CODE_PERMISSIONS = 2;
    private Uri pdfFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        rlUploadPdf = findViewById(R.id.rlUploadPdf);
        rlUploadPdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Permissions are granted, so create an intent to open the file picker
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");

                    // Start the file picker activity and wait for the result
                    startActivityForResult(Intent.createChooser(intent, "Select PDF"), REQUEST_CODE_PICK_PDF_FILE);
                } else {
                    // Permissions are not granted, so request them
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_PDF_FILE && resultCode == RESULT_OK && data != null) {
            // Get the URI of the selected PDF file
            pdfFileUri = data.getData();

            // TODO: Handle the selected PDF file, for example by displaying it using a PDF viewer library
        }
    }
}