package com.ntubus.ntubus.entity.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class BusPositionResponse {
    @ElementList(required = false, inline = true)
    public List<Device> devices;

    @Element(required = false)
    public String error;
}
