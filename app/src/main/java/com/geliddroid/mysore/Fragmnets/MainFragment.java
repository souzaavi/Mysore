package com.geliddroid.mysore.Fragmnets;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.geliddroid.mysore.AboutActivity;
import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.Apis.Config;
import com.geliddroid.mysore.CircularActivity;
import com.geliddroid.mysore.CustomWidget.CustomTextView;
import com.geliddroid.mysore.GospelActivity;
import com.geliddroid.mysore.InstitutionNamesActivity;
import com.geliddroid.mysore.MassTimmingsActivity;
import com.geliddroid.mysore.NewsDetails;
import com.geliddroid.mysore.OrganisationActivity;
import com.geliddroid.mysore.R;
import com.geliddroid.mysore.models.AboutUsList;
import com.geliddroid.mysore.models.News;
import com.geliddroid.mysore.models.NewsList;
import com.geliddroid.mysore.models.Wordofgod;
import com.geliddroid.mysore.models.WordofgodList;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

/**
 * Created by levin on 13-11-2017.
 */

public class MainFragment extends Fragment {
    RelativeLayout churchesPlaceVar;
    TextView churchesvar;
    Typeface myfont;
    ImageView btn;
    RelativeLayout bibleplaceholder;
    Boolean Flag = false;
    int OldLeft;
    CustomTextView read;

    private ArrayList<Wordofgod> wordofgodArrayList;
    private Call<WordofgodList> WordofgodListCall;
    int OldRight;
    private AdapterViewFlipper adapterViewFlipper;
    ApiServices apiServices;
    ArrayList<News> newsArrayList;
    retrofit2.Call<NewsList> newsListCall;

    TextView aboutUsVar, circularVar, intitutionVar, organizationVar, weekcount, Reading, Reflection;
    FlipperAdapter newsAdapter;
    Context context;
    private YouTubePlayer YPlayer;
    private String YOUTUBE_API_KEY = "AIzaSyC62muKjutsHarRaEBlCGWipwO4CDkC2_A";


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_fragment, container, false);
        context = getContext();

        myfont = Typeface.createFromAsset(getContext().getAssets(), "fonts/baarpb__.TTF");
        aboutUsVar = (TextView) view.findViewById(R.id.Aboutus);
        circularVar = (TextView) view.findViewById(R.id.circular);
        Reflection = (TextView) view.findViewById(R.id.ReflectioOftheDay);
        read = (CustomTextView) view.findViewById(R.id.read);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(context, GospelActivity.class);
            }
        });

        Reflection.setTypeface(myfont);


        //btn = (ImageView) view.findViewById(R.id.bibleLogo);
        churchesPlaceVar = (RelativeLayout) view.findViewById(R.id.churchesplace);
        churchesvar = (TextView) view.findViewById(R.id.churches);
        bibleplaceholder = view.findViewById(R.id.dailyQuote);
        intitutionVar = view.findViewById(R.id.institution);
        organizationVar = view.findViewById(R.id.organization);

        newsArrayList = new ArrayList<>();
        apiServices = ApiClient.getClient().create(ApiServices.class);
        adapterViewFlipper = (AdapterViewFlipper) view.findViewById(R.id.newsFliper);

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bibleplaceholder.getLayoutParams();

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Flag) {
                    OldLeft = params.leftMargin;
                    OldRight = params.rightMargin;
                    params.leftMargin = 0;
                    params.rightMargin = 0;
                    bibleplaceholder.setLayoutParams(params);
                    Flag = true;
                } else if (Flag) {
                    params.leftMargin = OldLeft;
                    params.rightMargin = OldRight;
                    bibleplaceholder.setLayoutParams(params);
                    Flag = false;
                }
            }
        });*/

        aboutUsVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(getContext(), AboutActivity.class);
            }
        });

        churchesvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(getContext(), MassTimmingsActivity.class);
            }
        });

        churchesPlaceVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(getContext(), MassTimmingsActivity.class);
            }
        });

        intitutionVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(getContext(), InstitutionNamesActivity.class);
            }
        });
        organizationVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(getContext(), OrganisationActivity.class);
            }
        });
        circularVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(getContext(), CircularActivity.class);
            }
        });
        Reflection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeActivity(getContext(), GospelActivity.class);
            }
        });

        aboutUsVar.setTypeface(myfont);
        circularVar.setTypeface(myfont);
        circularVar.setTypeface(myfont);
        intitutionVar.setTypeface(myfont);
        organizationVar.setTypeface(myfont);
        churchesvar.setTypeface(myfont);


        newsFlip();
        loadgospelTitle();

        return view;
    }

    private void loadgospelTitle() {
        String date = "2017-12-04";
        apiServices = ApiClient.getGospelClient().create(ApiServices.class);
        wordofgodArrayList = new ArrayList<>();
        WordofgodListCall = apiServices.getWordOfGod(date);
        WordofgodListCall.enqueue(new Callback<WordofgodList>() {
            @Override
            public void onResponse(Call<WordofgodList> call, Response<WordofgodList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getWordofgod() != null) {
                        int size = response.body().getWordofgod().size();
                        for (int i = 0; i < size; i++) {
                            wordofgodArrayList.add(response.body().getWordofgod().get(i));
                        }
                        Reflection.setText(wordofgodArrayList.get(size - 2).getTitle());
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<WordofgodList> call, Throwable t) {

            }
        });
    }

    public void invokeActivity(Context context, Class aClass) {
        Intent intent = new Intent(context, aClass);
        startActivity(intent);
    }

    public void newsFlip() {
        String date = "2017-11-01 23:26:29";
        apiServices = ApiClient.getClient().create(ApiServices.class);
        newsArrayList = new ArrayList<>();
        newsListCall = apiServices.getNewsList(date);
        newsListCall.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        int size = response.body().getNewsList().size();
                        for (int i = 0; i < size; i++) {
                            newsArrayList.add(response.body().getNewsList().get(i));
                        }
                        newsAdapter = new FlipperAdapter(context, newsArrayList, myfont);
                        adapterViewFlipper.setAdapter(newsAdapter);
                        adapterViewFlipper.setFlipInterval(5000);
                        adapterViewFlipper.setInAnimation(context, R.animator.fade_in);
                        adapterViewFlipper.setOutAnimation(context, R.animator.fade_out);
                        adapterViewFlipper.startFlipping();
                    } else {
                        Toast.makeText(context, "Null Boddy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Something Went wrong, pls Try After some time.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                Toast.makeText(context, "Communication Err", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class FlipperAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<News> newsArrayList;
    LayoutInflater inflater;
    private Typeface myfont;

    public FlipperAdapter(Context context, ArrayList<News> newsArrayList, Typeface myfont) {
        this.context = context;
        this.newsArrayList = newsArrayList;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myfont = myfont;
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final News news = newsArrayList.get(position);

        View view = inflater.inflate(R.layout.news_flipper, null);
        TextView newsTitleHolder = (TextView) view.findViewById(R.id.newsTitle);
        newsTitleHolder.setTypeface(myfont);
        ImageView newsImageHolder = (ImageView) view.findViewById(R.id.newsImage);
        newsTitleHolder.setText(news.getTitle());
        Glide.with(context).load(news.getLimg()).into(newsImageHolder);
        newsTitleHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetails.class);
                intent.putExtra("Data", news);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return view;
    }
}

