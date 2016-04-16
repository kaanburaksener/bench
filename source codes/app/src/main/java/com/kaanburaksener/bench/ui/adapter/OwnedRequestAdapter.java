package com.kaanburaksener.bench.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.Request;
import com.kaanburaksener.bench.handler.RequestHandler;
import com.kaanburaksener.bench.ui.activity.ViewApplicantsActivity;

import java.util.List;

/**
 * Created by kaanburaksener on 10/04/16.
 */
public class OwnedRequestAdapter extends RecyclerView.Adapter<OwnedRequestAdapter.OwnedRequestViewHolder> {
    private List<Request> requestList;
    private Activity activity;
    private Context context;
    private Context windowContext;

    public OwnedRequestAdapter(List<Request> requestList, Activity activity, Context context, Context windowContext) {
        this.requestList = requestList;
        this.activity = activity;
        this.context = context;
        this.windowContext = windowContext;
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    @Override
    public void onBindViewHolder(OwnedRequestViewHolder requestViewHolder, int i) {
        if(!requestList.isEmpty()) {
            final Request request = requestList.get(i);
            requestViewHolder.vTitle.setText(request.getTitle());
            requestViewHolder.vDescription.setText(request.getDescription());
            requestViewHolder.vLocation.setText(request.getLocation());
            requestViewHolder.vPlayerPosition.setText(request.getPlayerPosition());
            requestViewHolder.vTime.setText(request.getTime());
            requestViewHolder.vStatus.setText(request.getStatus());
            final int requestID = request.getID();
            final int statusID = request.getStatusID();

            if(statusID == 2) {
                requestViewHolder.vButtonHolder.setVisibility(View.GONE);
                requestViewHolder.vDivider.setVisibility(View.GONE);
            } else {
                requestViewHolder.vCloseRequestButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestHandler.performCloseRequest(requestID, activity, context, windowContext);
                    }
                });

                requestViewHolder.vViewApplicantsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ViewApplicantsActivity.class);
                        intent.putExtra("request_id", requestID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public OwnedRequestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.request_card_layout_two, viewGroup, false);

        return new OwnedRequestViewHolder(itemView);
    }

    public static class OwnedRequestViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitle;
        protected TextView vDescription;
        protected TextView vLocation;
        protected TextView vPlayerPosition;
        protected TextView vTime;
        protected TextView vStatus;
        protected TextView vViewApplicantsButton;
        protected TextView vCloseRequestButton;
        protected LinearLayout vButtonHolder;
        protected View vDivider;

        public OwnedRequestViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.title);
            vDescription =  (TextView) v.findViewById(R.id.description);
            vLocation = (TextView)  v.findViewById(R.id.location);
            vPlayerPosition = (TextView)  v.findViewById(R.id.playerPosition);
            vTime =  (TextView) v.findViewById(R.id.time);
            vStatus = (TextView)  v.findViewById(R.id.status);
            vViewApplicantsButton = (TextView)  v.findViewById(R.id.viewApplicantsButton);
            vCloseRequestButton = (TextView)  v.findViewById(R.id.closeRequestButton);
            vButtonHolder = (LinearLayout) v.findViewById(R.id.buttonHolder);
            vDivider = (View) v.findViewById(R.id.divider);
        }
    }
}
