package com.srujana.newsappcode;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.srujana.newsappcode.Models.Articles;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List <Articles> articles;

    public Adapter(Context context, List<Articles> articles){
        this.context =context;
        this.articles= articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {
        final Articles a= articles.get(position);

        holder.tvTitle.setText(a.getTitle());
        holder.tvSource.setText(a.getSource().getName());
        holder.tvDate.setText(a.getPublishedAt());

        String imageUrl=a.getUrlToImage();
        String url=a.getUrl();
        Picasso.with(context).load(imageUrl).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Using Intent to Open the detailed view and send Data
                Intent intent=new Intent(context, NewsDetailedView.class);
                intent.putExtra("title",a.getTitle());
                intent.putExtra("source",a.getSource().getName());
                intent.putExtra("description",a.getDescription());
                intent.putExtra("time",a.getPublishedAt());
                intent.putExtra("imageUrl",a.getUrlToImage());
                intent.putExtra("url",a.getUrl());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvSource,tvDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvSource= itemView.findViewById(R.id.tvSource);
            tvDate=itemView.findViewById(R.id.tvDate);
            imageView=itemView.findViewById(R.id.image);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }
}
