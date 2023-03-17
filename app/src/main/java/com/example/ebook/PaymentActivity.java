package com.example.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebook.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class PaymentActivity extends AppCompatActivity {


    RelativeLayout rlGpay, rlPaytm, rlPhonepe;
    CheckBox checkboxGpay, checkboxPaytm, checkboxPhonepe;
    ImageButton imageButton;
    TextView textPrice;
    String price;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);




        rlGpay = findViewById(R.id.rlGpay);
        rlPaytm = findViewById(R.id.rlPaytm);
        rlPhonepe = findViewById(R.id.rlPhonepe);
        checkboxGpay = findViewById(R.id.checkboxGpay);
        checkboxPaytm = findViewById(R.id.checkboxPaytm);
        checkboxPhonepe = findViewById(R.id.checkboxPhonepe);
        imageButton= findViewById(R.id.imageButton);
        textPrice = findViewById(R.id.textPrice);

        price = getIntent().getStringExtra("price");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        textPrice.setText("₹ "+getIntent().getStringExtra("price"));

        rlGpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gapay();



            }
        });

        rlPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytm();


            }
        });

        rlPhonepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phonepe();

            }
        });


        checkboxPhonepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonepe();
            }
        });

        checkboxPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytm();
            }
        });


        checkboxGpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gapay();
            }
        });





    }

    private void phonepe() {

        rlPhonepe.setBackground(getResources().getDrawable(R.drawable.bg_rl));
        rlPaytm.setBackgroundResource(R.drawable.unselected_bg);
        rlGpay.setBackgroundResource(R.drawable.unselected_bg);
        checkboxPhonepe.setChecked(true);
        checkboxPaytm.setChecked(false);
        checkboxGpay.setChecked(false);

        // Bottom dilog box open

        openButtomDialog();

    }



    private void paytm() {
        rlPaytm.setBackground(getResources().getDrawable(R.drawable.bg_rl));
        rlGpay.setBackgroundResource(R.drawable.unselected_bg);
        rlPhonepe.setBackgroundResource(R.drawable.unselected_bg);

        checkboxPaytm.setChecked(true);
        checkboxGpay.setChecked(false);
        checkboxPhonepe.setChecked(false);

        openButtomDialogPaytm();

    }


    private void gapay() {
        rlGpay.setBackground(getResources().getDrawable(R.drawable.bg_rl));
        rlPaytm.setBackgroundResource(R.drawable.unselected_bg);
        rlPhonepe.setBackgroundResource(R.drawable.unselected_bg);

        checkboxGpay.setChecked(true);
        checkboxPaytm.setChecked(false);
        checkboxPhonepe.setChecked(false);

        openButtomDialogGpay();


    }

    private void openButtomDialogGpay() {

        BottomSheetDialog bottomSheet = new BottomSheetDialog(PaymentActivity.this);
        bottomSheet.setContentView(R.layout.bottom_sheet_layout_gpay);
        bottomSheet.show();

        Button openappbutton = bottomSheet.findViewById(R.id.openappbutton);
        TextView UPI_ID = bottomSheet.findViewById(R.id.UPI_ID);

        UPI_ID.setText("1234567890@paytm");

        openappbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open app
                String upiId = "6382088746@paytm"; // replace with your UPI ID
                String amount =price; // replace with the transaction amount

               // open gpay

                Uri uri = Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", upiId)
                        .appendQueryParameter("pn", "Your Name")
                        .appendQueryParameter("tr", "transaction_id")
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        .build();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage("com.google.android.apps.nbu.paisa.user");
                startActivity(intent);

             //   startActivity(intent);

            startActivityForResult(intent, 1);



            }
        });



    }
    private void openButtomDialog() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(PaymentActivity.this);
        bottomSheet.setContentView(R.layout.bottom_sheet_layout);
        bottomSheet.show();


        Button openappbutton = bottomSheet.findViewById(R.id.openappbutton);
        TextView UPI_ID = bottomSheet.findViewById(R.id.UPI_ID);

        UPI_ID.setText("1234567890@paytm");

        openappbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open app
                String upiId = "6382088746@paytm"; // replace with your UPI ID
                String amount = "10.00"; // replace with the transaction amount

                Uri uri = Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", upiId)
                        .appendQueryParameter("pn", "Your Name")
                        .appendQueryParameter("tr", "transaction_id")
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        .build();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage("com.phonepe.app");
                //startActivity(intent);

               startActivityForResult(intent, 1);






            }
        });






    }

    private void openButtomDialogPaytm() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(PaymentActivity.this);
        bottomSheet.setContentView(R.layout.bottom_sheet_layout);
        bottomSheet.show();


        Button openappbutton = bottomSheet.findViewById(R.id.openappbutton);
        TextView UPI_ID = bottomSheet.findViewById(R.id.UPI_ID);

        UPI_ID.setText("1234567890@paytm");

        openappbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open app
                String upiId = "6382088746@paytm"; // replace with your UPI ID
                String amount = "10.00"; // replace with the transaction amount

                //open paytm
                Uri uri = Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", upiId)
                        .appendQueryParameter("pn", "Your Name")
                        .appendQueryParameter("tr", "transaction_id")
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        .build();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage("net.one97.paytm");

//           startActivity(intent);

        startActivityForResult(intent, 1);




            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Payment successful, move to next activity
                Intent intent = new Intent(this, LoadingActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("sub_name", getIntent().getStringExtra("sub_name"));
                intent.putExtra("code", getIntent().getStringExtra("code"));
                intent.putExtra("publication", getIntent().getStringExtra("publication"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("regulation", getIntent().getStringExtra("regulation"));
                intent.putExtra("image", getIntent().getStringExtra("image"));
                startActivity(intent);
            } else {

                // Payment successful, move to next activity
                Intent intent = new Intent(this, LoadingActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("sub_name", getIntent().getStringExtra("sub_name"));
                intent.putExtra("code", getIntent().getStringExtra("code"));
                intent.putExtra("publication", getIntent().getStringExtra("publication"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("regulation", getIntent().getStringExtra("regulation"));
                intent.putExtra("image", getIntent().getStringExtra("image"));
                startActivity(intent);
                // Payment failed, handle accordingly
            }
        }

    }

}