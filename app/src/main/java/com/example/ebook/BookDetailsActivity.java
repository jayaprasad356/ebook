package com.example.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ebook.helper.Session;

public class BookDetailsActivity extends AppCompatActivity {

    TextView tvBookName, tvcode, tvPublication, tvPrice, tvRegulation, tvName;
    ImageView ivBookImage;
    Button btnBuyNow;
    ImageButton ibBack;
    Activity activity;
    Session session;
    String id , name, code, publication, price, regulation, image, sub_name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);


        activity = BookDetailsActivity.this;
        session = new Session(activity);


        tvBookName = findViewById(R.id.tvBookName);
        tvcode = findViewById(R.id.tvcode);
        tvPublication = findViewById(R.id.tvPublication);
        tvPrice = findViewById(R.id.tvPrice);
        tvRegulation = findViewById(R.id.tvRegulation);
        tvName = findViewById(R.id.tvName);
        ivBookImage = findViewById(R.id.ivBookImage);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        ibBack = findViewById(R.id.ibBack);


        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        code = getIntent().getStringExtra("code");
        publication = getIntent().getStringExtra("publication");
        price = getIntent().getStringExtra("price");
        regulation = getIntent().getStringExtra("regulation");
        image = getIntent().getStringExtra("image");
        sub_name = getIntent().getStringExtra("sub_name");




        Glide.with(activity).load(getIntent().getStringExtra("image")).into(ivBookImage);
        tvBookName.setText(getIntent().getStringExtra("name"));
        tvcode.setText(getIntent().getStringExtra("code"));
        tvPublication.setText(getIntent().getStringExtra("publication"));
        tvPrice.setText("₹ "+getIntent().getStringExtra("price"));
        tvRegulation.setText(getIntent().getStringExtra("regulation"));
        tvName.setText(getIntent().getStringExtra("name")+" - "+getIntent().getStringExtra("sub_name"));

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, PaymentActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("code", code);
                intent.putExtra("publication", publication);
                intent.putExtra("price", price);
                intent.putExtra("regulation", regulation);
                intent.putExtra("image", image);
                intent.putExtra("sub_name", sub_name);

                startActivity(intent);

            }
        });


    }
}