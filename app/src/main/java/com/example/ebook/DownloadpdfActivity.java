package com.example.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.ebook.PdfViewActivity;
import com.example.ebook.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadpdfActivity extends AppCompatActivity {

    private static final String DOWNLOAD_URL = "https://ebooks.graymatterworks.com/storage/app/public/book/book-pdf/2023-03-16-6412b2664cfe2.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadpdf);

        Button downloadButton = findViewById(R.id.download_button);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAndOpenPDF(DOWNLOAD_URL);
            }
        });
    }

    private void downloadAndOpenPDF(String url) {
        String fileName = "my_fil1.pdf";
        File pdfFile = new File(getCacheDir(), fileName);


        if (pdfFile.exists()) {
            // If the file already exists, open it in the PDF viewer activity
            openPDFViewer(pdfFile);
        } else {
            // If the file doesn't exist, download it and save it
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Downloading PDF");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Handle download failure
                    progressDialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        // Handle download failure
                        progressDialog.dismiss();
                        return;
                    }

                    long fileSize = response.body().contentLength();
                    long bytesRead = 0;
                    byte[] buffer = new byte[1024];
                    int count;
                    FileOutputStream fos = new FileOutputStream(pdfFile);

                    while ((count = response.body().byteStream().read(buffer)) != -1) {
                        bytesRead += count;
                        fos.write(buffer, 0, count);
                        int progress = (int) (bytesRead * 100 / fileSize);
                        progressDialog.setProgress(progress);
                    }

                    fos.close();

                    progressDialog.dismiss();

                    // Open the PDF viewer activity
                    openPDFViewer(pdfFile);
                }
            });
        }
    }

    private void openPDFViewer(File pdfFile) {
        Intent intent = new Intent(this, PdfViewActivity.class);
        intent.putExtra("file_path", pdfFile.getAbsolutePath());
        startActivity(intent);
    }

}