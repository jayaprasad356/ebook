package com.example.ebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.canhub.cropper.CropImage;
import com.example.ebook.helper.ApiConfig;
import com.example.ebook.helper.Constant;
import com.example.ebook.helper.Session;
import com.google.android.material.divider.MaterialDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PaymentStatusActivity extends AppCompatActivity {

    TextView tvPrice;
    Activity activity;
    Session session;
    String price, name, sub_name, image, code, publication, regulation,bookid;
    CardView cardView;
    private static final int PICK_IMAGE = 100;
    ImageView ivImage,iv1;
    MaterialDivider divider1;
    String filePath1;
    Uri imageUri;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);




        activity = this;
        session = new Session(activity);

        tvPrice = findViewById(R.id.tvPrice);
        cardView = findViewById(R.id.cardView);
        ivImage = findViewById(R.id.ivImage);
        iv1 = findViewById(R.id.iv1);
        divider1 = findViewById(R.id.divider1);

        cardView.setOnClickListener(v -> {
            pickImageFromGallery();
        });


        tvPrice.setText("₹ "+getIntent().getStringExtra("price"));


        bookid = getIntent().getStringExtra("id");
        price = getIntent().getStringExtra("price");
        name = getIntent().getStringExtra("name");
        sub_name = getIntent().getStringExtra("sub_name");
        image = getIntent().getStringExtra("image");
        code = getIntent().getStringExtra("code");
        publication = getIntent().getStringExtra("publication");
        regulation = getIntent().getStringExtra("regulation");

    }




    private void order(String bookid) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, "4");
        params.put(Constant.BOOKID,"1");
        Map<String, String> FileParams = new HashMap<>();
        FileParams.put(Constant.IMAGE, filePath1);





        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Toast.makeText(activity, ""+jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(activity, PaymentStatusActivity.class);
                        intent.putExtra("id", bookid);
                        intent.putExtra("price", price);
                        intent.putExtra("name", name);
                        intent.putExtra("sub_name", sub_name);
                        intent.putExtra("image", image);
                        intent.putExtra("code", code);
                        intent.putExtra("publication", publication);
                        intent.putExtra("regulation", regulation);
                        startActivity(intent);





                    }
                    else {

                        Toast.makeText(activity, ""+jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                        if (jsonObject.getString(Constant.MESSAGE).equals("Book Already Purchased")){
                            Intent intent = new Intent(activity, PaymentStatusActivity.class);
                            intent.putExtra("id", bookid);
                            intent.putExtra("price", price);
                            intent.putExtra("name", name);
                            intent.putExtra("sub_name", sub_name);
                            intent.putExtra("image", image);
                            intent.putExtra("code", code);
                            intent.putExtra("publication", publication);
                            intent.putExtra("regulation", regulation);
                            startActivity(intent);

                        }

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }, activity, Constant.ORDER, params,true, 1);




    }



    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                if (requestCode == REQUEST_IMAGE_GALLERY) {
                    imageUri = data.getData();
                    CropImage.activity(imageUri)
//                            .setGuidelines(CropImageView.Guidelines.ON)
//                            .setOutputCompressQuality(90)
//                            .setRequestedSize(300, 300)
//                            .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
//                            .setAspectRatio(1, 1)
                            .start(activity);
                }

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                assert result != null;

                filePath1 = result.getUriFilePath(activity, true);

                File imgFile = new File(filePath1);

                if (imgFile.exists()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    ivImage.setImageBitmap(myBitmap);

                    order(bookid);

                }

            }


        }
    }


}