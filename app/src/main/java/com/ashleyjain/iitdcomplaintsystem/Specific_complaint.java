package com.ashleyjain.iitdcomplaintsystem;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class Specific_complaint extends ListFragment {
    public  Integer cId;
    TextView Title,Description;
    commentObjectAdapter adapter;
    public Specific_complaint() {
        // Required empty public constructor
    }
    String title,description,created_at,created_by,no_of_vote,com;
    String[] commentDescription,commentCreatedAt,commentCreatedBy;
    Integer[] commentId;
    private List<commentObject> commentObjectList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cId = getArguments().getInt("id");
        String url = "http://"+LoginActivity.ip+"/first/default/spec_complaint.json/"+cId;
        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                //dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject complaintObj = jsonObject.getJSONObject("complaint");
                    title = complaintObj.getString("title");
                    description = complaintObj.getString("description");
                    no_of_vote = complaintObj.getString("no_of_votes");
                    created_at = complaintObj.getString("created_at");
                    created_by = complaintObj.getString("user_id");
                    Title = (TextView) getActivity().findViewById(R.id.title);
                    Description = (TextView) getActivity().findViewById(R.id.description);
                    Title.setText(title);
                    Description.setText(description);
                    JSONArray comments = jsonObject.getJSONArray("comments");
                    commentDescription = new String[comments.length()];
                    commentCreatedAt = new String[comments.length()];
                    commentCreatedBy = new String[comments.length()];
                    commentId = new Integer[comments.length()];
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject com = comments.getJSONObject(i);
                        commentDescription[i] = com.getString("description");
                        commentCreatedAt[i] = com.getString("created_at");
                        commentCreatedBy[i] = com.getString("user_id");
                        commentId[i] = com.getInt("id");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commentObjectList = new ArrayList<commentObject>();

                for (int i = 0; i < commentDescription.length; i++) {

                    commentObject items = new commentObject(commentDescription[i],commentCreatedBy[i],commentCreatedBy[i],commentId[i]);
                    commentObjectList.add(items);

                }
                adapter = new commentObjectAdapter(getActivity(), commentObjectList ,cId);
                setListAdapter(adapter);

            }
        }, getActivity(), url, dialog);
        return inflater.inflate(R.layout.fragment_specific_complaint, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v, savedInstanceState);

        Button button = (Button) getActivity().findViewById(R.id.comment_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment = (EditText) getActivity().findViewById(R.id.comment);
                com = comment.getText().toString();
                com = com.replace(' ', '+');
                Toast.makeText(getActivity(), com, Toast.LENGTH_LONG).show();
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Authenticating...", true);
                String url = "http://" + LoginActivity.ip + "/first/default/post_comment.json?complaint_id=" + cId + "&description=" + com;

                //GET request through stringrequest
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            String success = jsonObject.getString("success");
                            if (success == "false") {
                                //user inputs are wrong
                                Toast.makeText(getActivity(), "Could not commented!!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Commented!!", Toast.LENGTH_LONG).show();
                                Specific_complaint fragment = new Specific_complaint();
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", cId);
                                fragment.setArguments(bundle);
                                replaceFragment(fragment);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, getActivity(), url, dialog);
            }
        });


    }

    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.pager, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }
}
