package com.ashleyjain.iitdcomplaintsystem;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        complaintObject assign_row = complaintList.get(position);
        // setting the image resource and title
        complaintTitle.setText(assign_row.getTitle());
        complaintDescription.setText(assign_row.getDescription());

        return convertView;
    }

}
