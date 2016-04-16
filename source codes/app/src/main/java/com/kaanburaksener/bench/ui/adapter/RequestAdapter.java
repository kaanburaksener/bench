package com.kaanburaksener.bench.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.Request;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.RequestApplicationHandler;

/**
 * Created by kaanburaksener on 08/04/16.
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private List<Request> requestList;
    private Activity activity;
    private Context context;
    private Context windowContext;
    private DBHandler dbHandler;

    public RequestAdapter(List<Request> requestList, Activity activity, Context context, Context windowContext) {
        this.requestList = requestList;
        this.activity = activity;
        this.context = context;
        this.windowContext = windowContext;
        this.dbHandler = new DBHandler(context);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    @Override
    public void onBindViewHolder(RequestViewHolder requestViewHolder, int i) {
        if(!requestList.isEmpty()) {
            Request request = requestList.get(i);
            requestViewHolder.vTitle.setText(request.getTitle());
            requestViewHolder.vDescription.setText(request.getDescription());
            requestViewHolder.vLocation.setText(request.getLocation());
            requestViewHolder.vPlayerPosition.setText(request.getPlayerPosition());
            requestViewHolder.vTime.setText(request.getTime());
            requestViewHolder.vStatus.setText(request.getStatus());
            requestViewHolder.vOwner.setText(request.getOwnerName());
            final int requestID = request.getID();
            final int userID = dbHandler.getUserId();

            requestViewHolder.vApplyRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestApplicationHandler.performCreateRequestApplication(requestID, userID, activity, context, windowContext);
                }
            });
        }
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.request_card_layout, viewGroup, false);

        return new RequestViewHolder(itemView);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitle;
        protected TextView vDescription;
        protected TextView vLocation;
        protected TextView vPlayerPosition;
        protected TextView vTime;
        protected TextView vStatus;
        protected TextView vOwner;
        protected TextView vApplyRequestButton;

        public RequestViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.title);
            vDescription =  (TextView) v.findViewById(R.id.description);
            vLocation = (TextView)  v.findViewById(R.id.location);
            vPlayerPosition = (TextView)  v.findViewById(R.id.playerPosition);
            vTime =  (TextView) v.findViewById(R.id.time);
            vStatus = (TextView)  v.findViewById(R.id.status);
            vOwner = (TextView)  v.findViewById(R.id.owner);
            vApplyRequestButton = (TextView) v.findViewById(R.id.applyRequestButton);
        }
    }
}
