package com.ashleyjain.iitdcomplaintsystem;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_Complaint extends Fragment {

    Button submitButton;


    public Edit_Complaint() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.edit_complaint_layout, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v, savedInstanceState);

        submitButton = (Button) getActivity().findViewById(R.id.update);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Voting...", true);
                String url = "http://" + LoginActivity.ip + "/first/complaint/edit_complaint.json?complaint_id=";
                //GET request through stringrequest
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            String success = jsonObject.getString("success");
                            Integer votes = jsonObject.getInt("no_of_votes");
                            System.out.println("<<<<<-----votes--->>>>>>>>>>>>>" + votes);
                            if (success == "false") {
                                //user inputs are wrong
                                Toast.makeText(getActivity(), "Fail!!! Try Again!!", Toast.LENGTH_LONG).show();
                            } else {
                                TextView votesText = (TextView) getView().findViewById(R.id.no_of_votes);
                                if (votes == null) {
                                } else {
                                    votesText.setText(votes.toString());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, getActivity(), url, dialog);
            }
        });

    }

}
