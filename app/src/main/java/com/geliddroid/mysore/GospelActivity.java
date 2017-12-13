package com.geliddroid.mysore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.CustomWidget.CustomTabLayout;
import com.geliddroid.mysore.models.Wordofgod;
import com.geliddroid.mysore.models.WordofgodList;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GospelActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    boolean flag = false;
    Typeface myfont;
    Typeface myfont1;
    ViewPager viewPager;
    CustomTabLayout tabLayout;
    CustomSwipeAdapter adapter;
    ApiServices apiServices;
    private ArrayList<Wordofgod> wordofgodArrayList;
    private Call<WordofgodList> WordofgodListCall;
    int tabsize;
    private AppBarLayout appBarLayout;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormat1 = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);

    private CompactCalendarView compactCalendarView;

    private boolean isExpanded = false;
    private String subtitle;
    private Date currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gospel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Word Of God");

        appBarLayout = findViewById(R.id.AppbarLay);
        // Set up the CompactCalendarView
        compactCalendarView = findViewById(R.id.compactcalendar_view);

        // Force English
        compactCalendarView.setLocale(TimeZone.getDefault(), /*Locale.getDefault()*/Locale.ENGLISH);

        compactCalendarView.setShouldDrawDaysHeader(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat1.format(dateClicked));
                getWordofgod(dateFormat.format(dateClicked));
                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded, true);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        final ImageView arrow = findViewById(R.id.date_picker_arrow);

        RelativeLayout datePickerButton = findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                }

                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded, true);
            }
        });

        myfont = Typeface.createFromAsset(this.getAssets(), "fonts/baarpb__.TTF");
        myfont1 = Typeface.createFromAsset(this.getAssets(), "fonts/sophia_normal.TTF");
        viewPager = (ViewPager) findViewById(R.id.fragmentHolder);
        tabLayout = (CustomTabLayout) findViewById(R.id.tabs);

    }

    public void setCurrentDate(Date date) {
        setSubtitle(dateFormat1.format(date));
        if (compactCalendarView != null) {
            compactCalendarView.setCurrentDate(date);
            getWordofgod(dateFormat.format(date));
        }
    }

    private boolean getWordofgod(String date) {
        apiServices = ApiClient.getGospelClient().create(ApiServices.class);
        wordofgodArrayList = new ArrayList<>();
        WordofgodListCall = apiServices.getWordOfGod(date);

        WordofgodListCall.enqueue(new Callback<WordofgodList>() {
            @Override
            public void onResponse(Call<WordofgodList> call, Response<WordofgodList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess().equals("1")) {
                        int size = response.body().getWordofgod().size();
                        for (int i = 0; i < size; i++) {
                            wordofgodArrayList.add(response.body().getWordofgod().get(i));
                        }
                        adapter = new CustomSwipeAdapter(getApplicationContext(), wordofgodArrayList, myfont, myfont1);
                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        flag = true;

                    } else {
                        adapter = new CustomSwipeAdapter(getApplicationContext());
                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        Toast.makeText(getApplicationContext(), " " + response.body().getMessage() + " ", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WordofgodList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " " + t.getMessage() + " ", Toast.LENGTH_LONG).show();
            }
        });
        return flag;
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = findViewById(R.id.title);

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void setSubtitle(String subtitle) {
        TextView datePickerTextView = findViewById(R.id.date_picker_text_view);

        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}

class CustomSwipeAdapter extends PagerAdapter {
    Typeface myfont1;
    Typeface myfont;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Wordofgod> wordofgod;
    String[] PageTitle1 = {"1st Reading", "Psalm", "2nd Reading", "Gospel", "Reflection"};
    String[] PageTitle = {"1st Reading", "Psalm", "Gospel", "Reflection"};

    public CustomSwipeAdapter(Context applicationContext) {
        this.context = applicationContext;
        wordofgod = new ArrayList<Wordofgod>();
    }

    public String getPageTitle(int position) {
        String title = null;
        if (getCount() == 4)
            title = PageTitle[position];
        else if (getCount() == 5)
            title = PageTitle1[position];
        return title;
    }

    int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public CustomSwipeAdapter(Context context, ArrayList<Wordofgod> wordofgod, Typeface myfont, Typeface myfont1) {
        this.context = context;
        this.wordofgod = wordofgod;
        this.myfont = myfont;
        this.myfont1 = myfont1;
    }

    @Override
    public int getCount() {
        setSize(wordofgod.size());
        return wordofgod.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.gospel_layout, container, false);
        Wordofgod word = wordofgod.get(position);
        TextView author = (TextView) view.findViewById(R.id.author);
        TextView gospelTitle = (TextView) view.findViewById(R.id.gospelTitle);
        TextView wordOfGod = (TextView) view.findViewById(R.id.wordOfGod);
        Button button = (Button) view.findViewById(R.id.share);
        author.setText(word.getVerse());
        gospelTitle.setText(word.getTitle());
        wordOfGod.setText(word.getReading());
        container.addView(view);
        author.setTypeface(myfont1);
        gospelTitle.setTypeface(myfont);
        wordOfGod.setTypeface(myfont1);
        final String uri = "http://192.168.1.20/DailyGospel/index.php/Welcome?date="+ word.getDate();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Daily Gospel");
                intent.putExtra(Intent.EXTRA_TEXT,""+Uri.parse(uri));
                context.startActivity(Intent.createChooser(intent,"Share link!"));
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
