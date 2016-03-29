package com.ashleyjain.iitdcomplaintsystem;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_Complaint extends Fragment {

    Button submitButton,deleteButton;
    String stringtitle,stringdescription;
    EditText tittle,description;

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

        stringtitle = getArguments().getString("title");
        stringdescription = getArguments().getString("description");

        tittle = (EditText) getActivity().findViewById(R.id.edit_tittle);
        tittle.setText(stringtitle);
        description = (EditText) getActivity().findViewById(R.id.edit_description);
        description.setText(stringdescription);

        submitButton = (Button) getActivity().findViewById(R.id.update);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tittle = (EditText) getActivity().findViewById(R.id.edit_tittle);
                stringtitle = tittle.getText().toString();
                stringtitle = stringtitle.replace(' ','+');
                description = (EditText) getActivity().findViewById(R.id.edit_description);
                stringdescription = description.getText().toString();
                stringdescription = stringdescription.replace(' ', '+');
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Updating...", true);
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


        deleteButton = (Button) getActivity().findViewById(R.id.delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Deleting...", true);
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
