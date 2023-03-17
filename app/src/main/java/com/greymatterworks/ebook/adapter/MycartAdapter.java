package com.greymatterworks.ebook.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greymatterworks.ebook.BookDetailsActivity;
import com.greymatterworks.ebook.R;
import com.greymatterworks.ebook.model.MyCart;

import java.util.ArrayList;

public class MycartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<MyCart> myCarts;

    String type;
    public MycartAdapter(Activity activity, ArrayList<MyCart> myCarts) {
        this.activity = activity;
        this.myCarts = myCarts;
        this.type = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.mycart_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, @SuppressLint("RecyclerView") int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final MyCart myCart = myCarts.get(position);

        holder.tvBookName.setText(myCart.getSub_name());
        holder.tvcode.setText(myCart.getSub_name());
        holder.tvPublication.setText(myCart.getPublication());
        holder.tvRegulation.setText(myCart.getRegulation());

        Glide.with(activity).load(myCart.getImage()).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(activity, BookDetailsActivity.class);
                intent.putExtra("id", myCart.getId());
                intent.putExtra("sub_name", myCart.getSub_name());
                intent.putExtra("name", myCart.getName());
                intent.putExtra("code", myCart.getSub_code());
                intent.putExtra("publication", myCart.getPublication());
                intent.putExtra("price", myCart.getPrice());
                intent.putExtra("regulation", myCart.getRegulation());
                intent.putExtra("image", myCart.getImage());
                activity.startActivity(intent);

            }
        });


    }



    @Override
    public int getItemCount() {
        return myCarts.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView image;
        final TextView tvBookName,tvcode,tvPublication,tvRegulation;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvcode = itemView.findViewById(R.id.tvcode);
            tvPublication = itemView.findViewById(R.id.tvPublication);
            tvRegulation = itemView.findViewById(R.id.tvRegulation);

        }
    }
}
