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
import com.greymatterworks.ebook.DownloadpdfActivity;
import com.greymatterworks.ebook.PaymentStatusActivity;
import com.greymatterworks.ebook.PdfViewActivity;
import com.greymatterworks.ebook.R;
import com.greymatterworks.ebook.model.MyBooklist;

import java.util.ArrayList;

public class MyBookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<MyBooklist> myBooklists;

    String type;
    public MyBookListAdapter(Activity activity, ArrayList<MyBooklist> myBooklists) {
        this.activity = activity;
        this.myBooklists = myBooklists;
        this.type = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.mybooklist_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, @SuppressLint("RecyclerView") int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final MyBooklist myBooklist = myBooklists.get(position);


        holder.tvBookName.setText(myBooklist.getSub_name());
        holder.tvcode.setText(myBooklist.getSub_code());
        holder.tvPublication.setText(myBooklist.getPublication());
        holder.tvRegulation.setText(myBooklist.getRegulation());
        Glide.with(activity).load(myBooklist.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBooklist.getPayment_status().equals("1")){
                    Intent intent = new Intent(activity, DownloadpdfActivity.class);
                    intent.putExtra("bookId", myBooklist.getBook_id());
                    intent.putExtra("name", myBooklist.getName());
                    intent.putExtra("document", myBooklist.getDocument());
                    activity.startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, PaymentStatusActivity.class);
                    intent.putExtra("bookId", myBooklist.getBook_id());
//                intent.putExtra("name", myBooklist.getSub_name());
//                intent.putExtra("document", myBooklist.getDocument());

                    activity.startActivity(intent);
                }
            }
        });




    }



    @Override
    public int getItemCount() {
        return myBooklists.size();
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
