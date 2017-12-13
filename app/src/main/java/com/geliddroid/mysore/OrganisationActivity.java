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
import com.geliddroid.mysore.models.Organisation;
import com.geliddroid.mysore.models.OrganisationList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by levin on 27-11-2017.
 */

public class OrganisationActivity extends AppCompatActivity {
    String date = "2017-11-08 02:17:19";

    private RecyclerView list;
    private LinearLayoutManager layoutManager;
    private ApiServices apiServices;
    private ArrayList<Organisation> OrgArrayList;
    private Call<OrganisationList> OrgListCall;
    private OrgAdapter orgAdapter;
    Typeface myfont;
    Typeface myfont1;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation);
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
        OrgArrayList = new ArrayList<>();
        OrgListCall = apiServices.getOrgList(date);
        OrgListCall.enqueue(new Callback<OrganisationList>() {
            @Override
            public void onResponse(Call<OrganisationList> call, Response<OrganisationList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        int size = response.body().getOrganisation().size();
                        for (int i = 0; i < size; i++) {
                            OrgArrayList.add(response.body().getOrganisation().get(i));
                        }
                        orgAdapter = new OrgAdapter(getApplicationContext(), OrgArrayList, myfont, myfont1);
                        list.setAdapter(orgAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry! I can't Fetch Data now. Try After Sometime", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrganisationList> call, Throwable t) {

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

class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.MyviewHolder> {
    Typeface myfont;
    Typeface myfont1;
    Context context;
    private LayoutInflater inflater;
    ArrayList<Organisation> OrgArrayList;

    public OrgAdapter(Context context, ArrayList<Organisation> organisationsArrayList, Typeface myfont, Typeface myfont1) {
        this.context = context;
        this.myfont = myfont;
        this.myfont1 = myfont1;
        this.OrgArrayList = organisationsArrayList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.institution_card, parent, false);
        return new OrgAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, int position) {
        final Organisation Orgitem = OrgArrayList.get(position);
        holder.InsName.setText(Orgitem.getName());
        holder.InsName.setTypeface(myfont);
        holder.InsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrganisationDataActivity.class);
                intent.putExtra("InsId", Orgitem.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OrgArrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView InsName;

        public MyviewHolder(View itemView) {
            super(itemView);
            InsName = (TextView) itemView.findViewById(R.id.insName);
        }
    }
}