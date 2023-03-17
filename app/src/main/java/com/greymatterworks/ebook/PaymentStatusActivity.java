package com.greymatterworks.ebook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;
import com.greymatterworks.ebook.helper.ApiConfig;
import com.greymatterworks.ebook.helper.Constant;
import com.greymatterworks.ebook.helper.Session;
import com.greymatterworks.ebook.model.MyBooklist;
import com.google.android.material.divider.MaterialDivider;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentStatusActivity extends AppCompatActivity {

    TextView tvPrice, tvHelpBtn;
    Activity activity;
    Session session;
    String price, name, sub_name, image, code, publication, regulation, bookid;
    CardView cardView;
    private static final int PICK_IMAGE = 100;
    ImageView ivImage, iv1, iv2, iv3;
    MaterialDivider divider1, divider2;
    String filePath1;
    Uri imageUri;
    Button uploadImage;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        divider1 = findViewById(R.id.divider1);
        divider2 = findViewById(R.id.divider2);
        tvHelpBtn = findViewById(R.id.tv_help_btn);
        uploadImage = findViewById(R.id.upload_image);

        cardView.setOnClickListener(v -> {
            pickImageFromGallery();
        });
        uploadImage.setOnClickListener(v -> {
            order(bookid);
        });

        tvPrice.setText("₹ " + getIntent().getStringExtra("price"));

        tvHelpBtn.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=AuBooksTechnicalpublication"));
            intent.setPackage("org.telegram.messenger");
            startActivity(intent);

        });
        bookid = getIntent().getStringExtra("id");
        price = getIntent().getStringExtra("price");
        name = getIntent().getStringExtra("name");
        sub_name = getIntent().getStringExtra("sub_name");
        image = getIntent().getStringExtra("image");
        code = getIntent().getStringExtra("code");
        publication = getIntent().getStringExtra("publication");
        regulation = getIntent().getStringExtra("regulation");

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void order(String bookid) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));
        params.put(Constant.BOOKID, bookid);
        Map<String, String> FileParams = new HashMap<>();
        FileParams.put(Constant.IMAGE, filePath1);


        ApiConfig.RequestToVolleyMulti((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        iv1.setBackgroundColor(activity.getColor(R.color.green));
                        iv2.setBackgroundColor(activity.getColor(R.color.green));
                        divider1.setBackgroundColor(activity.getColor(R.color.green));

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


                    } else {

                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                        if (jsonObject.getString(Constant.MESSAGE).equals("Book Already Purchased")) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, activity, Constant.ORDER, params, FileParams);


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
                    uploadImage.setEnabled(true);

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    ivImage.setImageBitmap(myBitmap);

                    //order(bookid);

                }

            }


        }
    }

    private void booklist() {


        Map<String, String> params = new HashMap<>();

        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));
        params.put(Constant.BOOKID, bookid);

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {


                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        ArrayList<MyBooklist> myBooklists = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {

                                MyBooklist group = g.fromJson(jsonObject1.toString(), MyBooklist.class);
                                myBooklists.add(group);

                            } else {
                                break;
                            }
                        }

                    } else {
                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, activity, Constant.MYBOOKS, params, true, 1);

    }
}