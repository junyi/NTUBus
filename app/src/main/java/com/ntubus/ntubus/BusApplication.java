package com.ntubus.ntubus;

import android.app.Application;

import com.ntubus.ntubus.entity.BusStop;
import com.ntubus.ntubus.entity.Route;
import com.parse.Parse;
import com.parse.ParseObject;

public class BusApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(BusStop.class);
        ParseObject.registerSubclass(Route.class);
        Parse.initialize(this, "mFVYKBkCYyrQWKFvsJ2kqUk7UckugEerwAer40oj", "zCjbkJjkvE7aljhD6mFR0shJKBi9U1AlbkIJ2En1");

    }
}
