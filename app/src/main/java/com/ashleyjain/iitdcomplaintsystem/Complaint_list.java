package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
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
    String[] complaintTitle,complaintDescription,complantCreatedAt,complantCreatedBy;
    private List<complaintObject> complaintObjectList;
    complaintObjectAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cCode = getArguments().getString("cCode");
        String url = "http://10.192.40.180:8000/first/default/home.json";
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
                    complantCreatedAt = new String[arr.length()];
                    complantCreatedBy = new String[arr.length()];
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ass = arr.getJSONObject(i);
                        complaintTitle[i] = ass.getString("title");
                        complaintDescription[i] = ass.getString("description");
                        complantCreatedAt[i] = ass.getString("created_at");
                        complantCreatedBy[i] = ass.getString("user_id");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                complaintObjectList = new ArrayList<complaintObject>();

                for (int i = 0; i < complaintTitle.length; i++) {

                    complaintObject items = new complaintObject(complaintTitle[i],complaintDescription[i],complantCreatedAt[i],complantCreatedBy[i]);
                    complaintObjectList.add(items);

                }
                System.out.println(complaintObjectList.size());
                adapter = new complaintObjectAdapter(getActivity(), complaintObjectList);
///                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,assignName);
                setListAdapter(adapter);

            }
        }, getActivity(), url, dialog);
        return inflater.inflate(R.layout.fragment_complaint_list, container, false);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        Bundle bundle = new Bundle();
//        AssignmentAdapter fragment = new AssignmentAdapter();
//        bundle.putString("name" , assignName[position]);
//        bundle.putString("deadline" , assignDeadline[position]);
//        bundle.putString("description" , assignDescr[position]);
//        fragment.setArguments(bundle);
//        replaceFragment(fragment);

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