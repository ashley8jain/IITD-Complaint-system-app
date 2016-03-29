package com.ashleyjain.iitdcomplaintsystem;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class Specific_complaint extends ListFragment {


    public  Integer cId;
    TextView Title,Description,n_o_v;
    commentObjectAdapter adapter;
    ImageButton upvote,downvote;

    public Specific_complaint() {
        // Required empty public constructor
    }

    String title,description,created_at,created_by,com;
    String[] commentDescription,commentCreatedAt,commentCreatedBy;
    Integer[] commentId;
    Integer no_of_vote;
    private List<commentObject> commentObjectList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getting complaint_id
        cId = getArguments().getInt("id");

        String url = "http://"+LoginActivity.ip+"/first/default/spec_complaint.json/"+cId;
        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);

        //GET request through stringrequest
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject complaintObj = jsonObject.getJSONObject("complaint");
                    title = complaintObj.getString("title");
                    description = complaintObj.getString("description");
                    no_of_vote = complaintObj.getInt("no_of_votes");
                    created_at = complaintObj.getString("created_at");
                    created_by = complaintObj.getString("user_id");
                    Title = (TextView) getActivity().findViewById(R.id.title);
                    Description = (TextView) getActivity().findViewById(R.id.description);
                    n_o_v = (TextView) getActivity().findViewById(R.id.no_of_votes);
                    upvote = (ImageButton) getActivity().findViewById(R.id.upvote);
                    downvote = (ImageButton) getActivity().findViewById(R.id.downvote);

                    Title.setText(title);
                    Description.setText(description);

                    //getting comments response json
                    JSONArray comments = jsonObject.getJSONArray("comments");
                    commentDescription = new String[comments.length()];
                    commentCreatedAt = new String[comments.length()];
                    commentCreatedBy = new String[comments.length()];
                    commentId = new Integer[comments.length()];
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject com = comments.getJSONObject(i);
                        commentDescription[i] = com.getString("description");
                        commentCreatedAt[i] = com.getString("created_at");
                        commentCreatedBy[i] = com.getString("user_name");
                        commentId[i] = com.getInt("id");

                    }

                    n_o_v.setText(no_of_vote.toString());

                    //upvote button
                    upvote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Voting...", true);
                            String url = "http://"+ LoginActivity.ip +"/first/complaint/upvote.json?type=1&complaint_id="+cId;
                            //GET request through stringrequest
                            GETrequest.response(new GETrequest.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        String success = jsonObject.getString("success");
                                        Integer votes = jsonObject.getInt("no_of_votes");
                                        if (success == "false") {
                                            //user inputs are wrong
                                            Toast.makeText(getActivity(), "Fail!!! Try Again!!", Toast.LENGTH_LONG).show();
                                        } else {
                                            TextView votesText = (TextView) getView().findViewById(R.id.no_of_votes);
                                            if(votes==null){}
                                            else{ votesText.setText(votes.toString()); }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, getActivity(), url, dialog);
                        }
                    });


                    //downvote button
                    downvote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Voting...", true);
                            String url = "http://" + LoginActivity.ip + "/first/complaint/upvote.json?type=-1&complaint_id=" + cId;

                            //GET request through stringrequest
                            GETrequest.response(new GETrequest.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        String success = jsonObject.getString("success");
                                        Integer votes = jsonObject.getInt("no_of_votes");
                                        System.out.println("<<<<<-----votes--->>>>>>>>>>>>>"+ votes);
                                        if (success == "false") {
                                            //user inputs are wrong
                                            Toast.makeText(getActivity(), "Fail!!! Try Again!!", Toast.LENGTH_LONG).show();
                                        } else {
                                            TextView votesText = (TextView) getView().findViewById(R.id.no_of_votes);
                                            if(votes==null){}
                                            else{ votesText.setText(votes.toString()); }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, getActivity(), url, dialog);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commentObjectList = new ArrayList<commentObject>();

                for (int i = 0; i < commentDescription.length; i++) {
                    commentObject items = new commentObject(commentDescription[i],commentCreatedBy[i],commentCreatedAt[i],commentId[i]);
                    commentObjectList.add(items);

                }
                adapter = new commentObjectAdapter(getActivity(), commentObjectList, cId);
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
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
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
                                Toast.makeText(getActivity(), "Could not comment!!", Toast.LENGTH_LONG).show();
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
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }
}
