package com.greymatterworks.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.greymatterworks.ebook.helper.ApiConfig;
import com.greymatterworks.ebook.helper.Constant;
import com.greymatterworks.ebook.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class LoadingActivity extends AppCompatActivity {

    Activity activity;
    Session session;
    String price, name, sub_name, image, code, publication, regulation,bookid;
    Handler handler;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        activity = this;
        session = new Session(activity);



        bookid = getIntent().getStringExtra("id");
        Toast.makeText(activity, ""+bookid, Toast.LENGTH_SHORT).show();
        String userid = session.getData(Constant.USER_ID);
        price = getIntent().getStringExtra("price");
        name = getIntent().getStringExtra("name");
        sub_name = getIntent().getStringExtra("sub_name");
        image = getIntent().getStringExtra("image");
        code = getIntent().getStringExtra("code");
        publication = getIntent().getStringExtra("publication");
        regulation = getIntent().getStringExtra("regulation");





        handler = new Handler();
        GotoActivity();




    }

    private void GotoActivity() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

             order(bookid);


            }
        },10000);
    }

    private void order(String bookid) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));
        params.put(Constant.BOOKID,bookid);

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
}