package com.ntubus.ntubus.entity.xml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class BusEtaResponse {
    @ElementList(inline = true, name = "route")
    public List<Route> routes;


}
