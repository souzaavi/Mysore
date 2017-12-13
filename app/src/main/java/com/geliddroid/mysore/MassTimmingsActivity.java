package com.geliddroid.mysore;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.geliddroid.mysore.CustomWidget.CustomButton;
import com.geliddroid.mysore.CustomWidget.CustomTextView;
import com.geliddroid.mysore.CustomWidget.CustomTextViews;
import com.geliddroid.mysore.models.Mass;
import com.geliddroid.mysore.models.MassList;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MassTimmingsActivity extends AppCompatActivity {

    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    ApiServices apiServices;
    ArrayList<Mass> massArrayList;
    Call<MassList> massListCall;
    MassAdapter massAdapter;
    Typeface myfont;
    Typeface myfont1;
    TextView appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass_timmings);
        myfont = Typeface.createFromAsset(this.getAssets(), "fonts/baarpb__.TTF");
        myfont1 = Typeface.createFromAsset(this.getAssets(), "fonts/sophia_normal.TTF");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appTitle = (TextView) findViewById(R.id.appTitle);
        appTitle.setTypeface(myfont);
        initViews();
        loadJson();
    }

    private void loadJson() {
        apiServices = ApiClient.getClient().create(ApiServices.class);
        massArrayList = new ArrayList<>();
        String date = "2017-11-08 02:17:19";
        massListCall = apiServices.getMassList(date);
        massListCall.enqueue(new Callback<MassList>() {
            @Override
            public void onResponse(Call<MassList> call, Response<MassList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        int size = response.body().getMassList().size();
                        for (int i = 0; i < size; i++) {
                            massArrayList.add(response.body().getMassList().get(i));
                        }
                        massAdapter = new MassAdapter(getApplicationContext(), massArrayList, myfont, myfont1);
                        list.setAdapter(massAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry! I can't Fetch Data now. Try After Sometime", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MassList> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        list = (RecyclerView) findViewById(R.id.RVmass);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
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
                massAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}

class MassAdapter extends RecyclerView.Adapter<MassAdapter.MyViewHolderWithChild> implements Filterable {

    Typeface myfont;
    Typeface myfont1;
    Context context;
    ArrayList<Mass> MassArrayFilteredList;
    ArrayList<Mass> MassArrayList;
    LayoutInflater inflater;
    SparseBooleanArray expandState = new SparseBooleanArray();

    public MassAdapter(Context context, ArrayList<Mass> massArrayList, Typeface myfont, Typeface myfont1) {
        this.context = context;
        MassArrayFilteredList = massArrayList;
        MassArrayList = massArrayList;
        this.myfont = myfont;
        this.myfont1 = myfont1;
        for (int i = 0; i < massArrayList.size(); i++)
            expandState.append(i, false);
    }


    @Override
    public MyViewHolderWithChild onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.layout_withchild, parent, false);
        return new MyViewHolderWithChild(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolderWithChild holder, final int position) {
        final Mass massitem = MassArrayFilteredList.get(position);
        holder.TVChildParent.setText(massitem.getName());
        holder.TVChildParent.setTypeface(myfont);
        holder.EXLParentChild.setInRecyclerView(true);
        holder.EXLParentChild.setExpanded(expandState.get(position));
        holder.dailyMass.setText(massitem.getTiming());
        holder.Sundaymass.setText(massitem.getSuntiming());
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
        holder.TVChildParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.EXLParentChild.toggle();
            }
        });
        holder.RLExpandButton.setRotation(expandState.get(position) ? 180f : 0f);
        holder.RLExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.EXLParentChild.toggle();
            }
        });
        holder.NavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.co.in/maps?q=" + massitem.getName() + " " + massitem.getAddress();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });
        holder.TVChild.setText(massitem.getAddress());
        holder.adoration.setText(massitem.getAdoration());
        holder.contact.setText(massitem.getContact());
        holder.TVChild.setTypeface(myfont1);
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Uri = "http://testlink4clients.com/testlink/archdiocesan_app/masshare.php?id=" + massitem.getId();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Church in Bangalore");
                intent.putExtra(Intent.EXTRA_TEXT,""+ android.net.Uri.parse(Uri));
                context.startActivity(Intent.createChooser(intent,"Share link!"));
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
        return MassArrayFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    MassArrayFilteredList = MassArrayList;
                } else {
                    ArrayList<Mass> filteredList = new ArrayList<>();
                    for (Mass mass : MassArrayList) {
                        if (mass.getAddress().toLowerCase().contains(charString) || mass.getName().toLowerCase().contains(charString)) {
                            filteredList.add(mass);
                        }
                    }
                    MassArrayFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = MassArrayFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                MassArrayFilteredList = (ArrayList<Mass>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolderWithChild extends RecyclerView.ViewHolder {
        public TextView TVChildParent, TVChild;
        public RelativeLayout RLExpandButton, NavigationButton;
        public ExpandableLinearLayout EXLParentChild;
        public CustomTextViews dailyMass, Sundaymass, adoration, contact;
        public CustomButton share;

        public MyViewHolderWithChild(View itemView) {
            super(itemView);
            //Toast.makeText(context,"holder const",Toast.LENGTH_LONG).show();
            TVChild = (TextView) itemView.findViewById(R.id.Child);
            TVChildParent = (TextView) itemView.findViewById(R.id.ChildParent);
            RLExpandButton = (RelativeLayout) itemView.findViewById(R.id.Expandbutton);
            EXLParentChild = (ExpandableLinearLayout) itemView.findViewById(R.id.ParentChild);
            NavigationButton = (RelativeLayout) itemView.findViewById(R.id.Navbutton);
            dailyMass = (CustomTextViews) itemView.findViewById(R.id.dailyMass);
            Sundaymass = (CustomTextViews) itemView.findViewById(R.id.sunmass);
            adoration = (CustomTextViews) itemView.findViewById(R.id.adoration);
            contact = (CustomTextViews) itemView.findViewById(R.id.contact);
            share = (CustomButton) itemView.findViewById(R.id.share);
        }
    }
}

