package com.ashleyjain.iitdcomplaintsystem;

import android.app.Application;

/**
 * Created by saurabh on 29/3/16.
 */
public class iitcomplaint_app extends Application {
    private String current_user_id;

    public String getLocalHost() {
        return current_user_id;
    }

    public void setLocalHost(String someVariable) {
        this.current_user_id = someVariable;
    }
}
