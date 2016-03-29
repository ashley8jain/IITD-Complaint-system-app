package com.ashleyjain.iitdcomplaintsystem.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ashleyjain.iitdcomplaintsystem.LoginActivity;
import com.ashleyjain.iitdcomplaintsystem.MainActivity;
import com.ashleyjain.iitdcomplaintsystem.R;
import com.ashleyjain.iitdcomplaintsystem.SpecificComplaint;
import com.ashleyjain.iitdcomplaintsystem.adapter.complaintObjectAdapter;
import com.ashleyjain.iitdcomplaintsystem.functions.GETrequest;
import com.ashleyjain.iitdcomplaintsystem.iitcomplaint_app;
import com.ashleyjain.iitdcomplaintsystem.objects.complaintObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndividualFragment extends ListFragment {

    String[] complaintTitle,complaintDescription,compliantCreatedAt,compliantCreatedBy;
    int[] complaintId, complaintVotes;
    Integer[] compliantDepartment;
    private List<complaintObject> complaintObjectList;
    complaintObjectAdapter adapter;
    Integer filter = MainActivity.filter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String url = "http://"+ LoginActivity.ip+"/first/default/home.json?level="+1+"&display_dept="+filter;
        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);

        //GET request through stringrequest
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arr = jsonObject.getJSONArray("my_hostel_complaints");
                    System.out.println(arr);
                    Integer len = arr.length();
                    complaintTitle = new String[len];
                    complaintDescription = new String[len];
                    compliantCreatedAt = new String[len];
                    compliantCreatedBy = new String[len];
                    complaintId = new int[len];
                    complaintVotes = new int[len];
                    compliantDepartment = new Integer[len];
                    for (int i = 0; i < len; i++) {
                        JSONObject ass = arr.getJSONObject(i);
                        complaintTitle[i] = ass.getString("title");
                        complaintDescription[i] = ass.getString("description");
                        compliantCreatedAt[i] = ass.getString("created_at");
                        compliantCreatedBy[i] = ass.getString("user_id");
                        complaintVotes[i] = ass.getInt("no_of_votes");
                        complaintId[i] = ass.getInt("id");
                        compliantDepartment[i] = ass.getInt("department");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                complaintObjectList = new ArrayList<complaintObject>();

                for (int i = 0; i < complaintTitle.length; i++) {
                    String v = Integer.toString(complaintVotes[i]);
                    complaintObject items = new complaintObject(complaintTitle[i], complaintDescription[i], v, compliantCreatedAt[i], compliantDepartment[i]);
                    complaintObjectList.add(items);

                }
                System.out.println(complaintObjectList.size());
                adapter = new complaintObjectAdapter(getActivity(), complaintObjectList);
                setListAdapter(adapter);

            }
        }, getActivity(), url, dialog);
        return inflater.inflate(R.layout.fragment_complaint_list, container, false);
    }

    //selecting specific complaint
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent myIntent = new Intent(getContext(), SpecificComplaint.class);
        myIntent.putExtra("id",complaintId[position]);
        MainActivity mainActivity = (MainActivity) getActivity();
        iitcomplaint_app app = (iitcomplaint_app) mainActivity.getApplication();
        myIntent.putExtra("current_user_id",app.getuserid());
        startActivity(myIntent);

        super.onListItemClick(l, v, position, id);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
