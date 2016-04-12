package com.kaanburaksener.bench.callback;

import org.json.JSONArray;

/**
 * Created by kaanburaksener on 12/04/16.
 */
public interface VolleyCallback {
    void onSuccess(JSONArray jsonArray);
    void onError(String msg);
}