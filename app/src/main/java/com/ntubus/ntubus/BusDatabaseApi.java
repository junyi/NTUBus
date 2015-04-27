package com.ntubus.ntubus;

import com.ntubus.ntubus.entity.BusStop;
import com.ntubus.ntubus.entity.Route;
import com.ntubus.ntubus.entity.RouteBusStop;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class BusDatabaseApi {
    public static void getBusStops(FindCallback<BusStop> callback) {
        ParseQuery<BusStop> query = ParseQuery.getQuery(BusStop.class);
        query.findInBackground(callback);
    }

    public static void getBusStops(String routeId, final FindCallback<BusStop> callback) {
        ParseQuery<RouteBusStop> query = ParseQuery.getQuery(RouteBusStop.class);
        query.whereEqualTo("route_id", routeId);
        query.include("bus_stop_parse_id");
        query.findInBackground(new FindCallback<RouteBusStop>() {
            @Override
            public void done(List<RouteBusStop> list, ParseException e) {
                ArrayList<BusStop> result = new ArrayList<BusStop>();

                if (e == null) {
                    for (RouteBusStop r : list) {
                        result.add((BusStop) r.getParseObject("bus_stop_parse_id"));
                    }
                }

                callback.done(result, e);
            }
        });
    }

    public static void getRoutes(FindCallback<Route> callback) {
        ParseQuery<Route> query = ParseQuery.getQuery(Route.class);
        query.findInBackground(callback);
    }

}
