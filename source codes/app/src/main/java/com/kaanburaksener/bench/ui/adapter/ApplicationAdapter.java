package com.kaanburaksener.bench.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.RequestApplication;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.ChatRequestHandler;
import com.kaanburaksener.bench.handler.RequestApplicationHandler;
import com.twilio.twiliochat.activities.MainChatActivity;
import com.twilio.twiliochat.application.TwilioChatApplication;
import com.twilio.twiliochat.interfaces.LoginListener;
import com.twilio.twiliochat.ipmessaging.IPMessagingClientManager;
import com.twilio.twiliochat.util.SessionManager;

import java.util.List;

/**
 * Created by kaanburaksener on 10/04/16.
 */
public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {
    private List<RequestApplication> applicationList;
    private Activity activity;
    private Context context;
    private Context windowContext;
    private DBHandler dbHandler;
    private IPMessagingClientManager messagingClient;

    public ApplicationAdapter(List<RequestApplication> applicationList, Activity activity, Context context, Context windowContext) {
        this.applicationList = applicationList;
        this.activity = activity;
        this.context = context;
        this.windowContext = windowContext;
        this.dbHandler = new DBHandler(context);
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder applicationViewHolder, int i) {
        if(!applicationList.isEmpty()) {
            RequestApplication requestApplication = applicationList.get(i);
            applicationViewHolder.vTitle.setText(requestApplication.getTitle());
            applicationViewHolder.vLocation.setText(requestApplication.getLocation());
            applicationViewHolder.vPlayerPosition.setText(requestApplication.getPlayerPosition());
            applicationViewHolder.vTime.setText(requestApplication.getTime());
            applicationViewHolder.vRequestStatus.setText("Request is " + requestApplication.getRequestStatus());
            applicationViewHolder.vOwner.setText(requestApplication.getOwnerName());
            applicationViewHolder.vApplicationStatus.setText("Application is " + requestApplication.getApplicationStatus());
            final int userID = dbHandler.getUserId();
            final int requestID = requestApplication.getRequestID();
            final int applicationStatusID = requestApplication.getApplicationStatusID();
            final int receiverID = requestApplication.getOwnerID();

            if(applicationStatusID != 2) {
                applicationViewHolder.vStartChatButton.setVisibility(View.GONE);
            } else {
                applicationViewHolder.vStartChatButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChatRequestHandler.performMakeChatRequest(userID, receiverID, requestID, context);
                        messagingClient = TwilioChatApplication.get().getIPMessagingClient();
                        SessionManager.getInstance().createLoginSession(dbHandler.getUserName());
                        messagingClient.connectClient(new LoginListener() {
                            @Override
                            public void onLoginFinished() {
                                final Intent intent = new Intent(context, MainChatActivity.class);
                                intent.putExtra("incoming class", 0);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onLoginError(String errorMessage) {
                            }
                        });
                    }
                });
            }

            applicationViewHolder.vCancelApplicationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestApplicationHandler.performCancelRequestApplication(requestID, userID, activity, context, windowContext);
                    dbHandler.deleteNotifiedAcceptedApplication(requestID);
                }
            });
        }
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
        protected TextView vStartChatButton;

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
            vStartChatButton = (TextView) v.findViewById(R.id.startChatButton);
        }
    }
}
