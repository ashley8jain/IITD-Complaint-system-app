package com.ashleyjain.iitdcomplaintsystem;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class NotificationFragment extends Fragment implements AdapterView.OnItemClickListener{
    Boolean IsNotif = true;
    private JSONArray notifsJSON;
    private ListView listView;
    Context context;
    ArrayList<String> stringList = new ArrayList<String>();
    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        try {
            IsNotif = getArguments().getBoolean("IsNotif");
            if(IsNotif){
                JSONObject jsonObject = (new JSONObject(getArguments().getString("notJSON")));
                notifsJSON = jsonObject.getJSONArray("notifications");
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        listView = (ListView) view.findViewById(R.id.notif_listView);


        if(IsNotif) {
            TextView tv = (TextView) view.findViewById(R.id.isNotif);
            for (int i = 0; i < notifsJSON.length(); i++) {
                try {
                    JSONObject notif = notifsJSON.getJSONObject(i);
                    stringList.add(new String(notif.getString("description")));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            tv.setText(String.format("You have %d notifications", stringList.size()));
            customAdapter adapter = new customAdapter(inflater.getContext(), stringList);
            listView.setAdapter(adapter);
        }
        else{
            TextView tv = (TextView) view.findViewById(R.id.isNotif);
            tv.setText("You have 0 notifications");
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Scanner in = new Scanner(stringList.get(position)).useDelimiter("[^0-9]+");
//        int integer = in.nextInt();
//        Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
//        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Specific_complaint fragment = new Specific_complaint();
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", integer);
//        fragment.setArguments(bundle);
//        fragmentTransaction.addToBackStack(fragment.toString());
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();

    }

    private class customAdapter extends ArrayAdapter<String>   {
        private View v;
        private ArrayList<String> stringArrayList;
        Context con;
        public customAdapter(Context context,ArrayList<String> sList){
            super(context, android.R.layout.simple_list_item_1);
            stringArrayList = sList;
            this.con =context;
        }

        @Override
        public int getCount() {
            return stringArrayList.size();
        }

        @Override
        public String getItem(int position) {
            return stringArrayList.get(position);
        }

        @Override
        public View getView(final int pos, View convertView, final ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(android.R.layout.simple_list_item_1, null);
            TextView tv = (TextView) v.findViewById(android.R.id.text1);
            tv.setText(Html.fromHtml(stringArrayList.get(pos)));


//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Scanner in = new Scanner(stringArrayList.get(pos)).useDelimiter("[^0-9]+");
//                     int integer = in.nextInt();
//                    FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Specific_complaint fragment = new Specific_complaint();
//                     Bundle bundle = new Bundle();
//                     bundle.putInt("id", integer);
//                     fragment.setArguments(bundle);
//                     fragmentTransaction.addToBackStack(fragment.toString());
//                     fragmentTransaction.replace(R.id.fragment_container, fragment);
//                     fragmentTransaction.commit();
//                }
//            });
            return v;
        }


    }

}
