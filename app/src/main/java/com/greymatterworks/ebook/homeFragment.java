package com.greymatterworks.ebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatterworks.ebook.adapter.BookListAdapter;
import com.greymatterworks.ebook.helper.ApiConfig;
import com.greymatterworks.ebook.helper.Constant;
import com.greymatterworks.ebook.helper.Session;
import com.greymatterworks.ebook.model.Booklist;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class homeFragment extends Fragment {


    TextView tvName;
    Activity activity ;
    Session session ;
    ImageButton ibProfile;
    RecyclerView recyclerView;
    BookListAdapter bookListAdapter;
    EditText etSearch;


    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        session = new Session(activity);

        tvName = root.findViewById(R.id.tvName);
        tvName.setText(session.getData(Constant.NAME));
        ibProfile = root.findViewById(R.id.ibProfile);
        recyclerView = root.findViewById(R.id.recyclerView);
        etSearch=root.findViewById(R.id.etSearch);


        ibProfile.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ProfileActivity.class);
            startActivity(intent);
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){
                    searchBookList();

                }

            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        booklist();

        return  root;
    }

    private void booklist() {


        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.USER_ID));

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {


                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        ArrayList<Booklist> booklists = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {

                                Booklist group = g.fromJson(jsonObject1.toString(), Booklist.class);
                                booklists.add(group);

                            } else {
                                break;
                            }
                        }

                        bookListAdapter = new BookListAdapter(activity, booklists);
                        recyclerView.setAdapter(bookListAdapter);




                    }
                    else {
                        Toast.makeText(activity, ""+jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }, activity, Constant.BOOKLIST, params,true, 1);





    }
    private void searchBookList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.SEARCH,etSearch.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {


                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        ArrayList<Booklist> booklists = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {

                                Booklist group = g.fromJson(jsonObject1.toString(), Booklist.class);
                                booklists.add(group);

                            } else {
                                break;
                            }
                        }

                        bookListAdapter = new BookListAdapter(activity, booklists);
                        recyclerView.setAdapter(bookListAdapter);


                    }
                    else {
                        Toast.makeText(activity, ""+jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }, activity, Constant.SEARCHBOOKS, params,true, 1);







    }
}