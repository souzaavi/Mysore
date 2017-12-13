package com.geliddroid.mysore;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.models.AboutUs;
import com.geliddroid.mysore.models.AboutUsList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {

    TextView AboutTitle;
    TextView AboutDiscription;
    ApiServices apiServices;
    ArrayList<AboutUs> aboutUsArrayList;
    Call<AboutUsList> AboutUsListCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final Typeface myfont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/sophia_normal.TTF");
        final Typeface myfont1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/baarpb__.TTF");
        String date = "2017-11-02 21:25:53";
        apiServices = ApiClient.getClient().create(ApiServices.class);
        aboutUsArrayList = new ArrayList<>();
        AboutTitle = (TextView) findViewById(R.id.AboutTitle);
        AboutDiscription = (TextView) findViewById(R.id.AboutDis);
        AboutUsListCall = apiServices.getAboutUsList(date);
        AboutUsListCall.enqueue(new Callback<AboutUsList>() {
            @Override
            public void onResponse(Call<AboutUsList> call, Response<AboutUsList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getAboutList() != null) {
                        int size = response.body().getAboutList().size();
                        Toast.makeText(getApplicationContext(), "Size " + size, Toast.LENGTH_LONG).show();
                        for (int i = 0; i < size; i++) {
                            aboutUsArrayList.add(response.body().getAboutList().get(i));
                        }
                        AboutTitle.setText(aboutUsArrayList.get(0).getTitle());
                        AboutTitle.setTypeface(myfont1);
                        AboutDiscription.setText(aboutUsArrayList.get(0).getContent());
                        AboutDiscription.setTypeface(myfont);
                    } else {
                        Toast.makeText(getApplicationContext(), "Content Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry! Something Went Wrong. Kindly Check your Network Connection", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AboutUsList> call, Throwable t) {

            }
        });
    }
}
