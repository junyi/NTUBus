package com.ntubus.ntubus.entity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("BusStop")
public class BusStop extends ParseObject implements Comparable<BusStop> {
    @Override
    public int compareTo(BusStop another) {
        return getString("description").compareTo(another.getString("description"));
    }
}
