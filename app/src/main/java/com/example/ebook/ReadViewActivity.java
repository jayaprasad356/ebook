package com.example.ebook;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class ReadViewActivity extends AppCompatActivity {

    
    WebView webview;
    private Button previousButton;
    private Button nextButton;
    String Document;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_view);

        Document = "https://ebooks.graymatterworks.com/storage/app/public/book/book-pdf/2023-03-12-640d7f600a2c4.pdf";

        WebView webView = findViewById(R.id.webView);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);

        String url = getPdfUrl();
        String pdfUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
        String pdfHtml = "<html><body><embed src='" + pdfUrl + "' width='100%' height='100%'></embed></body></html>";
        webView.loadData(pdfHtml, "text/html", "UTF-8");

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });
    }

    public String getPdfUrl() {
        return  Document;
    }
}

   


