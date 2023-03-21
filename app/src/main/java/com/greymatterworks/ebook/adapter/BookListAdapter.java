package com.greymatterworks.ebook.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greymatterworks.ebook.BookDetailsActivity;
import com.greymatterworks.ebook.R;
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

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Booklist> booklists;
    boolean isAddedToCart = false;
    Session session;

    String type;

    public BookListAdapter(Activity activity, ArrayList<Booklist> booklists) {
        this.activity = activity;
        this.booklists = booklists;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.booklist_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, @SuppressLint("RecyclerView") int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Booklist booklist = booklists.get(position);


        holder.tvBookName.setText(booklist.getSub_name());
        holder.tvcode.setText(booklist.getSub_code());
        holder.tvPublication.setText(booklist.getPublication());
        holder.tvPrice.setText("₹ " + booklist.getPrice());
        holder.tvRegulation.setText(booklist.getRegulation());
        Glide.with(activity).load(booklist.getImage()).placeholder(R.drawable.ic_book).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, BookDetailsActivity.class);
                intent.putExtra("id", booklist.getId());
                intent.putExtra("sub_name", booklist.getSub_name());
                intent.putExtra("name", booklist.getName());
                intent.putExtra("code", booklist.getSub_code());
                intent.putExtra("publication", booklist.getPublication());
                intent.putExtra("price", booklist.getPrice());
                intent.putExtra("regulation", booklist.getRegulation());
                intent.putExtra("image", booklist.getImage());
                activity.startActivity(intent);
            }
        });

        if (booklist.getCart_status() != null) {
            if (booklist.getCart_status().equals("1")) {

                holder.addcart.setImageResource(R.drawable.ic_baseline_favorite_24);

                holder.addcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        holder.addcart.setImageResource(R.drawable.ic_fav);
                        String bookid = booklist.getId();
                        deletecart(bookid);


                    }
                });


            } else if (booklist.getCart_status().equals("0")) {

                holder.addcart.setImageResource(R.drawable.ic_fav);
                holder.addcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        holder.addcart.setImageResource(R.drawable.ic_baseline_favorite_24);
                        String bookid = booklist.getId();
                        addcart(bookid);

                    }
                });

            }
        }


    }

    private void deletecart(String bookid) {
        session = new Session(activity);
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

                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, activity, Constant.REMOVE_FROM_CART, params, true, 1);


    }

    private void addcart(String bookid) {
        session = new Session(activity);
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

                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, activity, Constant.ADD_TO_CART, params, true, 1);


    }


    @Override
    public int getItemCount() {
        return booklists.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView image, addcart;
        final TextView tvBookName, tvcode, tvPublication, tvPrice, tvRegulation;

        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvcode = itemView.findViewById(R.id.tvcode);
            tvPublication = itemView.findViewById(R.id.tvPublication);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRegulation = itemView.findViewById(R.id.tvRegulation);
            addcart = itemView.findViewById(R.id.addcart);


        }
    }
}
