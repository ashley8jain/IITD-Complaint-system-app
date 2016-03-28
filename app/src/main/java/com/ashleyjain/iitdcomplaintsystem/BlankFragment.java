package com.ashleyjain.iitdcomplaintsystem;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends ListFragment {
    public String cCode;
    String[] complaintTitle,complaintDescription,compliantCreatedAt,compliantCreatedBy;
    Integer[] complaintId;
    private List<complaintObject> complaintObjectList;
    complaintObjectAdapter adapter;
    Integer filter = MainActivity.filter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String url = "http://"+LoginActivity.ip+"/first/default/home.json?level="+1+"&display_dept="+filter;
        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                //dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arr = jsonObject.getJSONArray("my_hostel_complaints");
                    System.out.println(arr);
                    complaintTitle = new String[arr.length()];
                    complaintDescription = new String[arr.length()];
                    compliantCreatedAt = new String[arr.length()];
                    compliantCreatedBy = new String[arr.length()];
                    complaintId = new Integer[arr.length()];
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ass = arr.getJSONObject(i);
                        complaintTitle[i] = ass.getString("title");
                        complaintDescription[i] = ass.getString("description");
                        compliantCreatedAt[i] = ass.getString("created_at");
                        compliantCreatedBy[i] = ass.getString("user_id");
                        complaintId[i] = ass.getInt("id");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                complaintObjectList = new ArrayList<complaintObject>();

                for (int i = 0; i < complaintTitle.length; i++) {

                    complaintObject items = new complaintObject(complaintTitle[i], complaintDescription[i], compliantCreatedAt[i], compliantCreatedBy[i]);
                    complaintObjectList.add(items);

                }
                System.out.println(complaintObjectList.size());
                adapter = new complaintObjectAdapter(getActivity(), complaintObjectList);
                setListAdapter(adapter);

            }
        }, getActivity(), url, dialog);
        return inflater.inflate(R.layout.fragment_complaint_list, container, false);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Bundle bundle = new Bundle();
//        Specific_complaint fragment = new Specific_complaint();
        Intent myIntent = new Intent(getContext(), SpecificComplaint.class);
        startActivity(myIntent);
        bundle.putInt("id", complaintId[position]);
        Toast.makeText(getContext(),Integer.toString(complaintId[position]),Toast.LENGTH_SHORT).show();
        super.onListItemClick(l, v, position, id);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
