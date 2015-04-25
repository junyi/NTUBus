package com.ntubus.ntubus;

import com.ntubus.ntubus.entity.BusStop;
import com.ntubus.ntubus.entity.Route;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseQuery;

public class BusDatabaseApi {
    public static void getBusStops(FindCallback<BusStop> callback){
        ParseQuery<BusStop> query = ParseQuery.getQuery(BusStop.class);
        query.findInBackground(callback);
    }

    public static void getRoutes(FindCallback<Route> callback){
        ParseQuery<Route> query = ParseQuery.getQuery(Route.class);
        query.findInBackground(callback);
    }

}
