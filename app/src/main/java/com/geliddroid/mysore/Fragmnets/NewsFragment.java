package com.geliddroid.mysore.Fragmnets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.CustomWidget.CustomTextView;
import com.geliddroid.mysore.CustomWidget.CustomTextViews;
import com.geliddroid.mysore.NewsDetails;
import com.geliddroid.mysore.R;
import com.geliddroid.mysore.models.News;
import com.geliddroid.mysore.models.NewsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by levin on 13-11-2017.
 */

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ApiServices apiServices;
    ArrayList<News> newsArrayList;
    retrofit2.Call<NewsList> newsListCall;
    NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        apiServices = ApiClient.getClient().create(ApiServices.class);
        newsArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.rvNews);
        getNews();
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        return view;
    }

    public void getNews() {
        String date = "2017-11-01 23:26:29";
        newsListCall = apiServices.getNewsList(date);
        newsListCall.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(retrofit2.Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getNewsList() != null) {
                        int size = response.body().getNewsList().size();
                        for (int i = 0; i < size; i++) {
                            newsArrayList.add(response.body().getNewsList().get(i));
                        }
                        newsAdapter = new NewsAdapter(getContext(), newsArrayList);
                        recyclerView.setAdapter(newsAdapter);
                    } else {
                        Toast.makeText(getContext(), "No Items Found", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getContext(), "Sorry! Something Went Wrong", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<NewsList> call, Throwable t) {

            }
        });
    }
}

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyviewHolder> {
    Context context;
    ArrayList<News> newsArrayList;
    LayoutInflater inflater;

    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.news_card, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        final News newsItem = newsArrayList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.date.setText(newsItem.getDate());

        Picasso.with(context).load(newsItem.getLimg()).placeholder(R.drawable.primary_gradient).into(holder.imageView);
        holder.llNewsid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetails.class);
                intent.putExtra("Data", newsItem);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CustomTextViews date;
        CustomTextView title;
        RelativeLayout llNewsid;

        public MyviewHolder(View itemView) {
            super(itemView);
            date = (CustomTextViews) itemView.findViewById(R.id.news_date);
            title = (CustomTextView) itemView.findViewById(R.id.news_title);
            imageView = (ImageView) itemView.findViewById(R.id.news_image);
            llNewsid = (RelativeLayout) itemView.findViewById(R.id.newsId);
        }
    }
}