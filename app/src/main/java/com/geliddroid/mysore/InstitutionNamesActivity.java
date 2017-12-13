package com.geliddroid.mysore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.models.InstitutionName;
import com.geliddroid.mysore.models.InstitutionNameList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstitutionNamesActivity extends AppCompatActivity {
    String date = "2017-11-08 02:17:19";

    private RecyclerView list;
    private LinearLayoutManager layoutManager;
    private ApiServices apiServices;
    private ArrayList<InstitutionName> InsNamesArrayList;
    private Call<InstitutionNameList> InstitutionNameListCall;
    private InstitutionNamesAdapter institutionNamesAdapter;
    Typeface myfont;
    Typeface myfont1;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myfont = Typeface.createFromAsset(this.getAssets(), "fonts/baarpb__.TTF");
        myfont1 = Typeface.createFromAsset(this.getAssets(), "fonts/sophia_normal.TTF");
        appname = (TextView) findViewById(R.id.appTitle);
        appname.setTypeface(myfont);
        initViews();
        loadJson();
    }

    private void loadJson() {
        apiServices = ApiClient.getClient().create(ApiServices.class);
        InsNamesArrayList = new ArrayList<>();
        InstitutionNameListCall = apiServices.getInstitutionNameList(date);
        InstitutionNameListCall.enqueue(new Callback<InstitutionNameList>() {
            @Override
            public void onResponse(Call<InstitutionNameList> call, Response<InstitutionNameList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        int size = response.body().getInstitutionNames().size();
                        for (int i = 0; i < size; i++) {
                            InsNamesArrayList.add(response.body().getInstitutionNames().get(i));
                        }
                        institutionNamesAdapter = new InstitutionNamesAdapter(getApplicationContext(), InsNamesArrayList, myfont, myfont1);
                        list.setAdapter(institutionNamesAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry! I can't Fetch Data now. Try After Sometime", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<InstitutionNameList> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        list = (RecyclerView) findViewById(R.id.rvInsName);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
    }
}

class InstitutionNamesAdapter extends RecyclerView.Adapter<InstitutionNamesAdapter.MyviewHolder> {
    Typeface myfont1;
    Typeface myfont;
    Context context;
    private LayoutInflater inflater;
    ArrayList<InstitutionName> InsNameArrayList;

    public InstitutionNamesAdapter(Context context, ArrayList<InstitutionName> InsNameArrayList, Typeface myfont, Typeface myfont1) {
        this.context = context;
        this.InsNameArrayList = InsNameArrayList;
        this.myfont = myfont;
        this.myfont1 = myfont1;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.institution_card, parent, false);
        return new InstitutionNamesAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, int position) {
        final InstitutionName Insitem = InsNameArrayList.get(position);
        holder.InsName.setText(Insitem.getName());
        holder.InsName.setTypeface(myfont);
        holder.InsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InstitutionData.class);
                intent.putExtra("InsId", Insitem.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return InsNameArrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView InsName;

        public MyviewHolder(View itemView) {
            super(itemView);
            InsName = (TextView) itemView.findViewById(R.id.insName);
        }
    }
}