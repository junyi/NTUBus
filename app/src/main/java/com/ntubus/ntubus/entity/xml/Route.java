package com.ntubus.ntubus.entity.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class Route {
    @Attribute(name = "id")
    public String id;

    @Attribute(name = "name")
    public String name;

    @ElementList(inline = true)
    public List<Bus> busList;
}
