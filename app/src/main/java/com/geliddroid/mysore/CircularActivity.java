package com.geliddroid.mysore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.R;
import com.geliddroid.mysore.models.Circular;
import com.geliddroid.mysore.models.CircularList;
import com.geliddroid.mysore.models.Data;
import com.geliddroid.mysore.models.DataList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CircularActivity extends AppCompatActivity {

    private String date;
    private RecyclerView list;
    private LinearLayoutManager layoutManager;
    private ApiServices apiServices;
    private ArrayList<Circular> CircularArrayList;
    private Call<CircularList> CircularListCall;
    private CircularAdapter circularAdapter;
    Typeface myfont;
    Typeface myfont1;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);
        myfont = Typeface.createFromAsset(this.getAssets(), "fonts/baarpb__.TTF");
        myfont1 = Typeface.createFromAsset(this.getAssets(), "fonts/sophia_normal.TTF");
        appname = (TextView) findViewById(R.id.appTitle);
        appname.setTypeface(myfont);
        initView();
        loadJson();
    }

    private void initView() {
        list = (RecyclerView) findViewById(R.id.rvCircular);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
    }

    private void loadJson() {
        apiServices = ApiClient.getClient().create(ApiServices.class);
        CircularArrayList = new ArrayList<>();
        CircularListCall = apiServices.getCircular(date);
        CircularListCall.enqueue(new Callback<CircularList>() {
            @Override
            public void onResponse(Call<CircularList> call, Response<CircularList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        int size = response.body().getCircular().size();
                        for (int i = 0; i < size; i++) {
                            CircularArrayList.add(response.body().getCircular().get(i));
                        }
                        circularAdapter = new CircularAdapter(getApplicationContext(), CircularArrayList, myfont, myfont1);
                        list.setAdapter(circularAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry! I can't Fetch Data now. Try After Sometime", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CircularList> call, Throwable t) {

            }
        });
    }
}

class CircularAdapter extends RecyclerView.Adapter<CircularAdapter.MyViewHolder> {
    Typeface myfont1;
    Typeface myfont;
    Context context;
    ArrayList<Circular> circularArrayFilteredList;
    ArrayList<Circular> circularArrayList;
    LayoutInflater inflater;
    SparseBooleanArray expandState = new SparseBooleanArray();

    public CircularAdapter(Context context, ArrayList<Circular> circularArrayList, Typeface myfont, Typeface myfont1) {
        this.context = context;
        this.circularArrayFilteredList = circularArrayList;
        this.circularArrayList = circularArrayList;
        this.myfont = myfont;
        this.myfont1 = myfont;
        for (int i = 0; i < circularArrayList.size(); i++)
            expandState.append(i, false);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.circular_card, parent, false);
        return new CircularAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Circular circular = circularArrayFilteredList.get(position);
        holder.CircularTitle.setText(circular.getName());
        holder.CircularTitle.setTypeface(myfont);
        holder.DownloadButton.setTypeface(myfont1);
        holder.DownloadButton.setOnClickListener(new View.OnClickListener() {
            String uri = circular.getLink();

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return circularArrayFilteredList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView CircularTitle;
        Button DownloadButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            CircularTitle = (TextView) itemView.findViewById(R.id.circularTitle);
            DownloadButton = (Button) itemView.findViewById(R.id.DownloadCircular);
        }
    }
}
