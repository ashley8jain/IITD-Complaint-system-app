package com.ashleyjain.iitdcomplaintsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by chandudasari on 27/03/16.
 */
public class commentObjectAdapter extends BaseAdapter  {
    Context context;
    List<commentObject> commentList;
    Integer cId;

    public commentObjectAdapter(Context context, List<commentObject> commentList,Integer cId) {

        this.context = context;
        this.commentList = commentList;
        this.cId = cId;

    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return commentList.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.comment_list_item, null);

        }


        ImageButton deleteImageView = (ImageButton) convertView.findViewById(R.id.delete);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(context, "", "Authenticating...", true);
                commentObject assign_row = commentList.get(position);
                String url = "http://" + LoginActivity.ip + "/first/default/delete_comment.json?comment_id="+assign_row.getId();

                //GET request through stringrequest
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            String success = jsonObject.getString("success");
                            if (success == "false") {
                                //user inputs are wrong
                                Toast.makeText(context, "Could not Delete!!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "!!", Toast.LENGTH_LONG).show();

                                //try to use Fragment instead of support fragment
                                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Specific_complaint fragment = new Specific_complaint();
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", cId);
                                fragment.setArguments(bundle);
                                fragmentTransaction.addToBackStack(fragment.toString());
                                fragmentTransaction.replace(R.id.fragment_container, fragment);
                                fragmentTransaction.commit();
//                                Specific_complaint fragment = new Specific_complaint();
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("id", cId);
//                                fragment.setArguments(bundle);
//                                replaceFragment(fragment);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, context, url, dialog);
            }
        });

        TextView commentDescription = (TextView) convertView.findViewById(R.id.comment);
        TextView commentBy = (TextView) convertView.findViewById(R.id.comment_by);
        TextView commentAt = (TextView) convertView.findViewById(R.id.comment_created_at);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.commentColorCode);

        commentObject assign_row = commentList.get(position);
        commentDescription.setText(assign_row.getDescription());
        commentBy.setText(assign_row.getCreated_by());
        commentAt.setText(assign_row.getCreated_at());


        return convertView;
    }



//    public void replaceFragment(Fragment courseFrag){
//        FragmentTransaction fragmentTransaction = ((Activity) context).getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
//        fragmentTransaction.addToBackStack(courseFrag.toString());
//        fragmentTransaction.commit();
//    }

}
