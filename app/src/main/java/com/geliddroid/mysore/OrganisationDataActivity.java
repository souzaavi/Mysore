package com.geliddroid.mysore;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.models.DataList;
import com.geliddroid.mysore.models.OrgData;
import com.geliddroid.mysore.models.OrgDataList;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganisationDataActivity extends AppCompatActivity {
    private String oid;
    private RecyclerView list;
    private LinearLayoutManager layoutManager;
    private ApiServices apiServices;
    private ArrayList<OrgData> OrgDataArrayList;
    private Call<OrgDataList> OrgDataListCall;
    private OrgDataAdapter orgDataAdapter;
    Typeface myfont;
    Typeface myfont1;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myfont = Typeface.createFromAsset(this.getAssets(), "fonts/baarpb__.TTF");
        myfont1 = Typeface.createFromAsset(this.getAssets(), "fonts/sophia_normal.TTF");
        appname = (TextView) findViewById(R.id.appTitle);
        appname.setTypeface(myfont);

        oid = (String) getIntent().getSerializableExtra("InsId");

        initViews();
        loadJson();
    }

    private void initViews() {
        list = (RecyclerView) findViewById(R.id.rvInsData);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
    }

    private void loadJson() {
        apiServices = ApiClient.getClient().create(ApiServices.class);
        OrgDataArrayList = new ArrayList<>();
        OrgDataListCall = apiServices.getOrgData(oid);
        OrgDataListCall.enqueue(new Callback<OrgDataList>() {
            @Override
            public void onResponse(Call<OrgDataList> call, Response<OrgDataList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        int size = response.body().getData().size();
                        for (int i = 0; i < size; i++) {
                            OrgDataArrayList.add(response.body().getData().get(i));
                        }
                        orgDataAdapter= new OrgDataAdapter(getApplicationContext(), OrgDataArrayList,myfont,myfont1);
                        list.setAdapter(orgDataAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry! I can't Fetch Data now. Try After Sometime", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgDataList> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data, menu);

        MenuItem search = menu.findItem(R.id.searchData);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                orgDataAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}

class OrgDataAdapter extends RecyclerView.Adapter<OrgDataAdapter.MyViewHolder> implements Filterable {
    Typeface myfont;
    Typeface myfont1;
    Context context;
    ArrayList<OrgData> DataArrayFilteredList;
    ArrayList<OrgData> DataArrayList;
    LayoutInflater inflater;
    SparseBooleanArray expandState = new SparseBooleanArray();

    public OrgDataAdapter(Context context, ArrayList<OrgData> insDataArrayList, Typeface myfont, Typeface myfont1) {
        this.context = context;
        DataArrayFilteredList = insDataArrayList;
        DataArrayList = insDataArrayList;
        this.myfont = myfont;
        this.myfont1 = myfont1;
        for (int i = 0; i < DataArrayList.size(); i++)
            expandState.append(i, false);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.institution_data_card, parent, false);
        return new OrgDataAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OrgData dataItem = DataArrayFilteredList.get(position);
        holder.InsName.setText(dataItem.getName());
        holder.InsName.setTypeface(myfont);
        holder.InsDescription.setText(dataItem.getDescription());
        holder.InsDescription.setTypeface(myfont1);
        holder.NavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.co.in/maps?q=" + dataItem.getName() + " " + dataItem.getDescription();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });

        holder.EXLParentChild.setInRecyclerView(true);
        holder.EXLParentChild.setExpanded(expandState.get(position));
        holder.EXLParentChild.setListener(new ExpandableLayoutListenerAdapter() {


            @Override
            public void onPreOpen() {
                changeRotate(holder.RLExpandButton, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                changeRotate(holder.RLExpandButton, 180f, 0f).start();
                expandState.put(position, false);
            }

        });
        holder.RLExpandButton.setRotation(expandState.get(position) ? 180f : 0f);
        holder.RLExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.EXLParentChild.toggle();
            }
        });

    }

    private ObjectAnimator changeRotate(RelativeLayout rlExpandButton, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlExpandButton, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @Override
    public int getItemCount() {
        return DataArrayFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    DataArrayFilteredList = DataArrayList;
                } else {
                    ArrayList<OrgData> filteredList = new ArrayList<>();
                    for (OrgData data : DataArrayList) {
                        if (data.getDescription().toLowerCase().contains(charString) || data.getName().toLowerCase().contains(charString)) {
                            filteredList.add(data);
                        }
                    }
                    DataArrayFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = DataArrayFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                DataArrayFilteredList = (ArrayList<OrgData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView InsName, InsDescription;
        public RelativeLayout RLExpandButton,NavigationButton;
        public ExpandableLinearLayout EXLParentChild;

        public MyViewHolder(View itemView) {
            super(itemView);
            InsName = (TextView) itemView.findViewById(R.id.cvInsName);
            InsDescription = (TextView) itemView.findViewById(R.id.cvInsDetails);
            RLExpandButton = (RelativeLayout) itemView.findViewById(R.id.Expandbutton);
            NavigationButton = (RelativeLayout) itemView.findViewById(R.id.NavigationButton);
            EXLParentChild = (ExpandableLinearLayout) itemView.findViewById(R.id.ParentChild);
        }
    }
}