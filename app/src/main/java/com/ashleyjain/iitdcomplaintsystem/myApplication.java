package com.ashleyjain.iitdcomplaintsystem;

import android.app.Application;

/**
 * Created by ashleyjain on 26/03/16.
 */
public class myApplication extends Application{

    private String localHost;

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String someVariable) {
        this.localHost = someVariable;
    }

}
