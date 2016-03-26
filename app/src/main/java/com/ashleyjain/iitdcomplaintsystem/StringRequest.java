package com.ashleyjain.iitdcomplaintsystem;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashleyjain on 26/03/16.
 */
public class StringRequest extends com.android.volley.toolbox.StringRequest {
    private final Response.Listener<String> mListener;

    /**
     * @param method
     * @param url
     *
     *            A {@link HashMap} to post with the request. Null is allowed
     *            and indicates no parameters will be posted along with request.
     * @param listener
     * @param errorListener
     */
    public StringRequest(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mListener = listener;
        Log.d("debug", "stringrequest");
    }
    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }
    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually
        LoginActivity.get().checkSessionCookie(response.headers);
        Log.d("debug", "checking cookie");

        return super.parseNetworkResponse(response);

    }

    /* (non-Javadoc)
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        LoginActivity.get().addSessionCookie(headers);
        Log.d("debug", "adding cookie");


        return headers;
    }
}
