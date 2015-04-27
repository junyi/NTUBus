package com.ntubus.ntubus.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ntubus.ntubus.BusApi;
import com.ntubus.ntubus.R;
import com.ntubus.ntubus.entity.BusStop;
import com.ntubus.ntubus.entity.Route;
import com.ntubus.ntubus.entity.xml.BusPositionResponse;
import com.ntubus.ntubus.entity.xml.Device;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    @InjectView(R.id.map)
    MapView mMapView;

    private GoogleMap mMap;

    private Handler handler = new Handler();

    private Runnable busPositionRunnable = new Runnable() {
        private Map<String, Marker> busPositionMarkers = new HashMap<>();
        private boolean error = false;

        @Override
        public void run() {
            try {
                BusApi.service.getCurrentBusPositions(new Callback<BusPositionResponse>() {
                    @Override
                    public void success(BusPositionResponse busPositionResponse, Response response) {
                        if (busPositionResponse.error != null) {
                            error = true;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "No buses found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            error = false;

                            Set<String> markersToRemove = new HashSet<>(busPositionMarkers.keySet());

                            List<Device> devices = busPositionResponse.devices;

                            int l = devices.size();

                            System.out.println("ntubus devices: " + l);

                            for (int i = 0; i < l; i++) {
                                Device device = devices.get(i);
                                if (busPositionMarkers.containsKey(device.busId)) {
                                    Marker marker = busPositionMarkers.get(device.busId);
                                    marker.setPosition(new LatLng(device.lat, device.lon));
                                    marker.setSnippet(device.routeName + ", " + device.speed);
                                    if (marker.isInfoWindowShown())
                                        marker.showInfoWindow();
                                    markersToRemove.remove(device.busId);
                                } else {
                                    MarkerOptions options = new MarkerOptions()
                                            .title(device.busName)
                                            .position(new LatLng(device.lat, device.lon))
                                            .snippet(device.routeName + ", " + device.speed);

                                    Marker marker = mMap.addMarker(options);
                                    busPositionMarkers.put(device.busId, marker);
                                }
                            }

                            Iterator<String> iterator = markersToRemove.iterator();
                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                if (busPositionMarkers.containsKey(key)) {
                                    System.out.println("Removing: " + key);
                                    busPositionMarkers.remove(key);
                                }
                            }

                            markersToRemove.clear();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error.getMessage());
                    }
                });

                handler.postDelayed(this, error ? 60 * 1000 : 3000);
                System.out.println(String.format("Checking for buses in next %s seconds.", error ? 60 : 3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment, container, false);
        ButterKnife.inject(this, view);
//        mMapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(getActivity().getApplicationContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        BusApi.database.getRoutes(new FindCallback<Route>() {
            @Override
            public void done(List<Route> list, ParseException e) {
                if (e == null) {
                    List<Integer> colors = new ArrayList<Integer>();
                    colors.add(R.color.md_red_300);
                    colors.add(R.color.md_blue_300);
                    colors.add(R.color.md_green_300);

                    int l = list.size();
                    for (int i = 0; i < l; i++) {
                        List<ParseGeoPoint> points = list.get(i).getList("zone");
                        PolylineOptions options = new PolylineOptions();

                        for (ParseGeoPoint p : points) {
                            options.add(new LatLng(p.getLatitude(), p.getLongitude()));
                        }

                        options.color(getResources().getColor(colors.get(i)));

                        mMap.addPolyline(options);
                    }


                } else {

                }
            }
        });

        BusApi.database.getBusStops(new FindCallback<BusStop>() {
            @Override
            public void done(List<BusStop> list, ParseException e) {
                if (e == null) {
                    for (BusStop busStop : list) {
                        ParseGeoPoint geoPoint = busStop.getParseGeoPoint("latlong");
                        MarkerOptions options = new MarkerOptions()
                                .title(busStop.getString("description"))
                                .position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                                .snippet(busStop.getString("road_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                        mMap.addMarker(options);
                    }
                } else {

                }
            }
        });

        handler.post(busPositionRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if (mMap != null)
            handler.post(busPositionRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        handler.removeCallbacks(busPositionRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        handler.removeCallbacks(busPositionRunnable);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
