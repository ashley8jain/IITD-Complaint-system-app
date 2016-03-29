package com.ashleyjain.iitdcomplaintsystem.functions;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ashleyjain.iitdcomplaintsystem.LoginActivity;

/**
 * Created by ashleyjain on 26/03/16.
 */
public class GETrequest {
    public static void response(final VolleyCallback callback, final Context context,String url, final ProgressDialog pd) {

        // Request a string response
        com.ashleyjain.iitdcomplaintsystem.StringRequest stringRequest = new com.ashleyjain.iitdcomplaintsystem.StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        // Result handling
                        pd.dismiss();
                        System.out.println(response);
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        pd.dismiss();
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("Something went wrong!");
                        error.printStackTrace();
                    }
                });



// Add the request to the queue
        LoginActivity.get().getRequestQueue().add(stringRequest);
    }
    public interface VolleyCallback{
        void onSuccess(String result);
    }
}
