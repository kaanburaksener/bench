package com.kaanburaksener.bench.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.Applicant;
import com.kaanburaksener.bench.core.User;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.RequestApplicationHandler;

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
        Applicant applicant = applicantList.get(i);
        applicantViewHolder.vName.setText(applicant.getName());
        applicantViewHolder.vLocation.setText(applicant.getLocation());
        applicantViewHolder.vBirthday.setText(applicant.getBirthday());
        applicantViewHolder.vApplicationStatus.setText(dbHandler.getApplicationStatusName(applicant.getApplicationStatusID()));
        final int userID = applicant.getUserID();

        if(applicant.getApplicationStatusID() != 1) {
            applicantViewHolder.vDivider.setVisibility(View.GONE);
            applicantViewHolder.vButtonHolder.setVisibility(View.GONE);
        } else if(applicant.getApplicationStatusID() == 2) {
            //Chat starts here!
        } else {
            applicantViewHolder.vAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int applicationStatusID = 2; //Accepted
                    RequestApplicationHandler.performFinalizeRequestApplication(requestID, userID, applicationStatusID, activity, context, windowContext);
                    // Service should inform the applicant about his / her application is accepted!
                }
            });

            applicantViewHolder.vRejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int applicationStatusID = 3; //Rejected
                    RequestApplicationHandler.performFinalizeRequestApplication(requestID, userID, applicationStatusID, activity, context, windowContext);
                }
            });
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
        }
    }
}
