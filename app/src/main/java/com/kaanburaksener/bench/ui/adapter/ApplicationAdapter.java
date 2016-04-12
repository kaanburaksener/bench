package com.kaanburaksener.bench.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.RequestApplication;

import java.util.List;

/**
 * Created by kaanburaksener on 10/04/16.
 */
public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {
    private List<RequestApplication> applicationList;

    public ApplicationAdapter(List<RequestApplication> applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder requestViewHolder, int i) {
        RequestApplication requestApplication = applicationList.get(i);
        requestViewHolder.vTitle.setText(requestApplication.getTitle());
        requestViewHolder.vLocation.setText(requestApplication.getLocation());
        requestViewHolder.vPlayerPosition.setText(requestApplication.getPlayerPosition());
        requestViewHolder.vTime.setText(requestApplication.getTime());
        requestViewHolder.vRequestStatus.setText(requestApplication.getRequestStatus());
        requestViewHolder.vOwner.setText(requestApplication.getOwnerName());
        requestViewHolder.vApplicationStatus.setText(requestApplication.getApplicationStatus());
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.application_card_layout, viewGroup, false);

        return new ApplicationViewHolder(itemView);
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vLocation;
        protected TextView vPlayerPosition;
        protected TextView vTime;
        protected TextView vRequestStatus;
        protected TextView vOwner;
        protected TextView vApplicationStatus;
        protected TextView vCancelApplicationButton;

        public ApplicationViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.title);
            vLocation = (TextView)  v.findViewById(R.id.location);
            vPlayerPosition = (TextView)  v.findViewById(R.id.playerPosition);
            vTime =  (TextView) v.findViewById(R.id.time);
            vRequestStatus =  (TextView) v.findViewById(R.id.requestStatus);
            vOwner = (TextView)  v.findViewById(R.id.owner);
            vApplicationStatus = (TextView)  v.findViewById(R.id.applicationStatus);
            vCancelApplicationButton = (TextView) v.findViewById(R.id.cancelApplicationButton);
        }
    }
}
