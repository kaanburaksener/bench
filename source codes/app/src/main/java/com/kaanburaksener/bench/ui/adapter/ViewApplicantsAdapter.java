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
import com.kaanburaksener.bench.core.Applicant;
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
 * Created by kaanburaksener on 12/04/16.
 */
public class ViewApplicantsAdapter extends RecyclerView.Adapter<ViewApplicantsAdapter.ApplicantViewHolder> {
    private List<Applicant> applicantList;
    private Activity activity;
    private Context context;
    private Context windowContext;
    private DBHandler dbHandler;
    private final int requestID;
    private IPMessagingClientManager messagingClient;

    public ViewApplicantsAdapter(int requestID, List<Applicant> applicantList, Activity activity, Context context, Context windowContext) {
        this.applicantList = applicantList;
        this.activity = activity;
        this.context = context;
        this.windowContext = windowContext;
        this.dbHandler = new DBHandler(context);
        this.requestID = requestID;
    }

    @Override
    public int getItemCount() {
        return applicantList.size();
    }

    @Override
    public void onBindViewHolder(ApplicantViewHolder applicantViewHolder, int i) {
        if(!applicantList.isEmpty()) {
            Applicant applicant = applicantList.get(i);
            applicantViewHolder.vName.setText(applicant.getName());
            applicantViewHolder.vLocation.setText(applicant.getLocation());
            applicantViewHolder.vBirthday.setText(applicant.getBirthday());
            applicantViewHolder.vApplicationStatus.setText(dbHandler.getApplicationStatusName(applicant.getApplicationStatusID()));
            final int userID = applicant.getUserID();
            final int starterID = dbHandler.getUserId();
            final int applicationStatus = applicant.getApplicationStatusID();

            if(applicationStatus == 1) {
                applicantViewHolder.vStartChatButton.setVisibility(View.GONE);

                applicantViewHolder.vAcceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int applicationStatusID = 2; //Accepted
                        RequestApplicationHandler.performFinalizeRequestApplication(requestID, userID, applicationStatusID, activity, context, windowContext);
                    }
                });

                applicantViewHolder.vRejectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int applicationStatusID = 3; //Rejected
                        RequestApplicationHandler.performFinalizeRequestApplication(requestID, userID, applicationStatusID, activity, context, windowContext);
                    }
                });
            } else if(applicationStatus == 2) {
                applicantViewHolder.vRejectButton.setVisibility(View.GONE);
                applicantViewHolder.vAcceptButton.setVisibility(View.GONE);

                applicantViewHolder.vStartChatButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChatRequestHandler.performMakeChatRequest(starterID, userID, requestID, context);
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
            } else if (applicationStatus == 3){
                applicantViewHolder.vDivider.setVisibility(View.GONE);
                applicantViewHolder.vButtonHolder.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public ApplicantViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.applicant_card_layout, viewGroup, false);

        return new ApplicantViewHolder(itemView);
    }

    public static class ApplicantViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vLocation;
        protected TextView vBirthday;
        protected TextView vApplicationStatus;
        protected TextView vAcceptButton;
        protected TextView vRejectButton;
        protected LinearLayout vButtonHolder;
        protected TextView vStartChatButton;
        protected View vDivider;

        public ApplicantViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            vLocation = (TextView)  v.findViewById(R.id.location);
            vBirthday = (TextView)  v.findViewById(R.id.birthday);
            vApplicationStatus = (TextView)  v.findViewById(R.id.applicationStatus);
            vAcceptButton = (TextView)  v.findViewById(R.id.acceptButton);
            vRejectButton = (TextView) v.findViewById(R.id.rejectButton);
            vButtonHolder = (LinearLayout) v.findViewById(R.id.buttonHolder);
            vDivider = (View) v.findViewById(R.id.divider);
            vStartChatButton = (TextView) v.findViewById(R.id.startChatButton);
        }
    }
}
