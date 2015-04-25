package com.ntubus.ntubus.entity.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Device {
    @Attribute(name = "route_name")
    public String routeName;

    @Attribute(name = "bus_id")
    public String busId;

    @Attribute(name = "bus_name")
    public String busName;

    @Attribute(name = "lat")
    public double lat;

    @Attribute(name = "lon")
    public double lon;

    @Attribute(name = "speed")
    public double speed;
}
