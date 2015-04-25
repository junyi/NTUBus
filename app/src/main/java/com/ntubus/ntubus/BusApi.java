package com.ntubus.ntubus;

import com.ntubus.ntubus.entity.xml.BusPositionResponse;
import com.ntubus.ntubus.entity.BusStop;
import com.ntubus.ntubus.entity.Route;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;
import retrofit.http.POST;

public class BusApi {
    public static BusApiService service;
    public static Database database;

    private static final String API_URL = "http://campusbus.ntu.edu.sg/ntubus/index.php/main";

    public interface BusApiService {
        @POST("/getCurrentPosition")
        void getCurrentBusPositions(Callback<BusPositionResponse> callback);
    }

    static {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setConverter(new SimpleXMLConverter())
                .build();

        service = restAdapter.create(BusApiService.class);
        database = new Database();
    }


    public static class Database {
        public static void getBusStops(FindCallback<BusStop> callback){
            ParseQuery<BusStop> query = ParseQuery.getQuery(BusStop.class);
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
            query.findInBackground(callback);
        }

        public static void getRoutes(FindCallback<Route> callback){
            ParseQuery<Route> query = ParseQuery.getQuery(Route.class);
            query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
            query.findInBackground(callback);
        }
    }
}
