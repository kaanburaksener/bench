package com.kaanburaksener.bench.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.callback.VolleyCallback;
import com.kaanburaksener.bench.core.Request;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.RequestHandler;
import com.kaanburaksener.bench.ui.adapter.RequestAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaanburaksener on 31/03/16.
 */
public class BrowseRequestsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private DBHandler dbHandler;
    private List<Request> requests;
    private RequestAdapter adapter;
    private RecyclerView recList;
    private LinearLayoutManager llm;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browse_requests,container,false);
        initializer(v);
        return v;
    }

    private void initializer(View v) {
        requests = new ArrayList<Request>();
        dbHandler = new DBHandler(mainActivity);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        recList = (RecyclerView) v.findViewById(R.id.requestList);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(mainActivity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        adapter = new RequestAdapter(requests, mainActivity, mainActivity.getApplicationContext(), mainActivity.getWindow().getContext());
        recList.setAdapter(adapter);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getOpenedRequests();
                                    }
                                }
        );
    }

    /**
     * This function is used to list the opened request by other users
     */

    public void getOpenedRequests(){
        swipeRefreshLayout.setRefreshing(true);

        RequestHandler.performGetOpenedRequests(dbHandler.getUserId(), mainActivity.getApplicationContext(), new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    JSONObject response = (JSONObject) jsonArray.get(0);
                    int id = Integer.valueOf(response.getString("id"));

                    if(id != -1) {
                        requests.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject request = (JSONObject) jsonArray.get(i);
                            com.kaanburaksener.bench.core.Request req = new com.kaanburaksener.bench.core.Request();

                            req.setID(Integer.parseInt(request.getString("id")));
                            req.setTitle(request.getString("title"));
                            req.setDescription(request.getString("description"));
                            req.setLocation(request.getString("location"));
                            req.setPlayerPosition(dbHandler.getPlayerPositionName(Integer.parseInt(request.getString("player_position_id"))));
                            req.setTime(request.getString("time"));
                            req.setStatus("Opened");
                            req.setOwnerName(request.getString("request_owner_name"));

                            requests.add(req);
                        }
                    }

                    adapter = new RequestAdapter(requests, mainActivity, mainActivity.getApplicationContext(), mainActivity.getWindow().getContext());
                    recList.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * This method is called when swipe refresh is pulled down
     */

    @Override
    public void onRefresh() {
        getOpenedRequests();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
