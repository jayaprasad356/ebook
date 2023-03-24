package com.greymatterworks.ebook.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.greymatterworks.ebook.ProfileActivity;
import com.greymatterworks.ebook.R;
import com.greymatterworks.ebook.activitys.PublishActivity;
import com.greymatterworks.ebook.helper.Constant;
import com.greymatterworks.ebook.helper.Session;

public class PublisherFragment extends Fragment {
    Activity activity;
    Session session;
    Button btnPublish;

    public PublisherFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_publisher, container, false);

        activity = getActivity();
        session = new Session(activity);

        btnPublish = root.findViewById(R.id.btnPublish);
        btnPublish.setOnClickListener(v -> {
            Intent intent = new Intent(activity, PublishActivity.class);
            startActivity(intent);
        });

        return  root;
    }
}