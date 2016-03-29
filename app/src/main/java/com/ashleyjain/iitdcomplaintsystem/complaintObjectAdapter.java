package com.ashleyjain.iitdcomplaintsystem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chandudasari on 27/03/16.
 */
public class complaintObjectAdapter extends BaseAdapter {

    Context context;
    List<complaintObject> complaintList;

    public complaintObjectAdapter(Context context, List<complaintObject> complaintList) {
        this.context = context;
        this.complaintList = complaintList;

    }

    @Override
    public int getCount() {
        return complaintList.size();
    }

    @Override
    public Object getItem(int position) {
        return complaintList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return complaintList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.complaint_list_item, null);
        }

        TextView complaintTitle = (TextView) convertView.findViewById(R.id.title);
        TextView complaintDescription = (TextView) convertView.findViewById(R.id.description);
        TextView createdAt = (TextView) convertView.findViewById(R.id.created_at);
        TextView Votes = (TextView) convertView.findViewById(R.id.no_of_votes);
        LinearLayout complaintColorCode = (LinearLayout) convertView.findViewById(R.id.complaintColorCode);

        Integer[] colors = {0xFF3F51B5,0xFFFF6600,0xFF009900,0xFFFF6600,0xFF33CCFF};

        complaintObject assign_row = complaintList.get(position);
        // setting the image resource and title
        complaintTitle.setText(assign_row.getTitle());
        complaintDescription.setText(assign_row.getDescription());
        createdAt.setText(assign_row.getCreated_at());
        Votes.setText(assign_row.getVotes());

        Integer s = assign_row.getDepartment_();
        if ( s==null ){
            complaintColorCode.setBackgroundColor(0xFFA6A6A6);
        }
        else{
            complaintColorCode.setBackgroundColor(colors[s-1]);
        }


        return convertView;
    }

}
