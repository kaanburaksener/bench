package com.kaanburaksener.bench.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.callback.VolleyCallback;
import com.kaanburaksener.bench.core.Applicant;
import com.kaanburaksener.bench.handler.RequestApplicationHandler;
import com.kaanburaksener.bench.ui.adapter.ViewApplicantsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaanburaksener on 12/04/16.
 */
public class ViewApplicantsActivity extends AppCompatActivity {
    private List<Applicant> applicants;
    private ViewApplicantsAdapter adapter;
    private RecyclerView recList;
    private LinearLayoutManager llm;
    private Activity activity;
    private int requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicants);
        Bundle bundle = getIntent().getExtras();
        requestID = bundle.getInt("request_id");
        initializer();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer() {
        setStatusBarColor();
        recList = (RecyclerView) findViewById(R.id.applicantList);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        activity = this;
        getApplicants();
    }

    /**
     * This function is used to change the status bar color
     */

    private void setStatusBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.color_primary_dark));
            }
        } catch (NumberFormatException e) {
            Log.d("Status bar set error: ", e.toString());
        }
    }

    public void getApplicants(){
        RequestApplicationHandler.performGetApplicants(requestID, activity.getApplicationContext(), new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    final ProgressDialog progressDialog = new ProgressDialog(getWindow().getContext());
                    progressDialog.setMessage("Request is being processed...");
                    progressDialog.show();
                    applicants = new ArrayList<Applicant>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject request = (JSONObject) jsonArray.get(i);
                        Applicant applicant = new Applicant();

                        applicant.setUserID(Integer.parseInt(request.getString("id")));
                        applicant.setName(request.getString("name"));
                        applicant.setLocation(request.getString("location"));
                        applicant.setBirthday(request.getString("birthday"));
                        applicant.setApplicationStatusID(Integer.parseInt((request.getString("application_status_id"))));

                        applicants.add(applicant);
                    }

                    adapter = new ViewApplicantsAdapter(requestID, applicants, activity, getApplicationContext(), getWindow().getContext());
                    recList.setAdapter(adapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }
}
