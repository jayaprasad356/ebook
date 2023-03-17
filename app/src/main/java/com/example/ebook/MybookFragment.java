package com.example.ebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebook.adapter.BookListAdapter;
import com.example.ebook.adapter.MyBookListAdapter;
import com.example.ebook.helper.ApiConfig;
import com.example.ebook.helper.Constant;
import com.example.ebook.helper.Session;
import com.example.ebook.model.Booklist;
import com.example.ebook.model.MyBooklist;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MybookFragment extends Fragment {


    TextView tvName;
    Activity activity ;
    Session session ;
    ImageButton ibProfile;
    RecyclerView recyclerView;
    MyBookListAdapter myBookListAdapter;


    public MybookFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mybook, container, false);

        activity = getActivity();
        session = new Session(activity);

        tvName = root.findViewById(R.id.tvName);
        tvName.setText(session.getData(Constant.NAME));
        ibProfile = root.findViewById(R.id.ibProfile);
        recyclerView = root.findViewById(R.id.recyclerView);

        ibProfile.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ProfileActivity.class);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        booklist();

        return  root;
    }

    private void booklist() {


        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));


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

                        myBookListAdapter = new MyBookListAdapter(activity, myBooklists);
                        recyclerView.setAdapter(myBookListAdapter);




                    }
                    else {
                        Toast.makeText(activity, ""+jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }, activity, Constant.MYBOOKS, params,true, 1);





    }
}