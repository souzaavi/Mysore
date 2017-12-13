package com.geliddroid.mysore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.geliddroid.mysore.CustomWidget.CustomTextView;
import com.geliddroid.mysore.CustomWidget.CustomTextViews;
import com.geliddroid.mysore.models.News;
import com.squareup.picasso.Picasso;


public class NewsDetails extends AppCompatActivity {
    CustomTextViews newsDate;
    CustomTextView newsTitle;
    CustomTextViews newsDescription;
    ImageView newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        newsDate = (CustomTextViews) findViewById(R.id.news_date);
        newsTitle = (CustomTextView) findViewById(R.id.news_title);
        newsDescription = (CustomTextViews) findViewById(R.id.newsDescription);
        newsImage = (ImageView) findViewById(R.id.news_image);

        News newsItem = (News) getIntent().getSerializableExtra("Data");
        newsDate.setText(newsItem.getDate());
        newsTitle.setText(newsItem.getTitle());
        newsDescription.setText(newsItem.getDescription());

        Picasso.with(getApplicationContext()).load(newsItem.getLimg()).placeholder(R.drawable.back1).into(newsImage);

    }
}
