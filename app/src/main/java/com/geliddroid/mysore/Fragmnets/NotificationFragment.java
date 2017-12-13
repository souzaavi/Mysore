package com.geliddroid.mysore.Fragmnets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.geliddroid.mysore.Apis.ApiClient;
import com.geliddroid.mysore.Apis.ApiServices;
import com.geliddroid.mysore.CustomWidget.CustomTextView;
import com.geliddroid.mysore.CustomWidget.CustomTextViews;
import com.geliddroid.mysore.R;
import com.geliddroid.mysore.models.Notification;
import com.geliddroid.mysore.models.NotificationList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by levin on 13-11-2017.
 */

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ApiServices apiServices;
    ArrayList<Notification> notificationArrayList;
    retrofit2.Call<NotificationList> notificationListCall;
    NotificationAdapter notificationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.notification_fragment, container, false);
        apiServices = ApiClient.getClient().create(ApiServices.class);
        notificationArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.rvNotification);
        getNotification();
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        return view;
    }

    private void getNotification() {
        String date = "2017-11-02 23:41:02";
        notificationListCall = apiServices.getNotificationList(date);
        notificationListCall.enqueue(new Callback<NotificationList>() {
            @Override
            public void onResponse(Call<NotificationList> call, Response<NotificationList> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE", "success");
                    if (response.body().getNotification() != null) {
                        int size = response.body().getNotification().size();
                        Log.d("RESPONSE", "guru success " + size);
                        for (int i = 0; i < size; i++) {
                            notificationArrayList.add(response.body().getNotification().get(i));
                        }
                        notificationAdapter = new NotificationAdapter(getContext(), notificationArrayList);
                        recyclerView.setAdapter(notificationAdapter);
                    } else {
                        Toast.makeText(getContext(), "Sorry! There are No Notification Yet", Toast.LENGTH_LONG);
                    }
                } else {
                    Toast.makeText(getContext(), "Sorry! Something Went Wrong.", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<NotificationList> call, Throwable t) {
                Toast.makeText(getContext(), "Sorry! Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolderNotification> {
    Context context;
    ArrayList<Notification> notificationArrayList;
    LayoutInflater inflater;

    public NotificationAdapter(Context context, ArrayList<Notification> notificationArrayList) {
        this.context = context;
        this.notificationArrayList = notificationArrayList;
    }

    @Override
    public ViewHolderNotification onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.notification_card, parent, false);
        return new ViewHolderNotification(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderNotification holder, int position) {
        Notification notificationitem = notificationArrayList.get(position);
        holder.vhDate.setText(notificationitem.getDate());
        holder.vhTitle.setText(notificationitem.getTitle());
        holder.vhDiscrip.setText(notificationitem.getDescription());
    }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    static class ViewHolderNotification extends RecyclerView.ViewHolder {
        private CustomTextView vhDate;
        private CustomTextView vhTitle;
        private CustomTextViews vhDiscrip;

        public ViewHolderNotification(View itemView) {
            super(itemView);
            vhDate = (CustomTextView) itemView.findViewById(R.id.cvnDate);
            vhTitle = (CustomTextView) itemView.findViewById(R.id.cvnTitle);
            vhDiscrip = (CustomTextViews) itemView.findViewById(R.id.cvnDiscription);
        }
    }
}
