package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Complaint_list extends ListFragment {

    public String cCode;
    String[] complaintTitle,complaintDescription,compliantCreatedAt,compliantCreatedBy;
    Integer[] compliantDepartment;
    int[] complaintId,complaintVotes;
    private List<complaintObject> complaintObjectList;
    complaintObjectAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cCode = getArguments().getString("cCode");
        String url = "http://"+LoginActivity.ip+"/first/default/home.json";
        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                //dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arr = jsonObject.getJSONArray("my_hostel_complaints");
                    System.out.println(arr);
                    Integer len =  arr.length();
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
                    complaintObject items = new complaintObject(complaintTitle[i],complaintDescription[i],v,compliantCreatedAt[i],compliantDepartment[i]);
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
        super.onListItemClick(l, v, position, id);
         Bundle bundle = new Bundle();
         Specific_complaint fragment = new Specific_complaint();
         bundle.putInt("id" , complaintId[position]);
//        bundle.putString("deadline" , assignDeadline[position]);
//        bundle.putString("description" , assignDescr[position]);
        fragment.setArguments(bundle);
        replaceFragment(fragment);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }
}