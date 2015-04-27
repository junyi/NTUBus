package com.ntubus.ntubus.entity.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Bus {
    @Attribute(name = "order")
    public int order;

    @Attribute(name = "name")
    public String name;

    @Attribute(name = "eta")
    public String eta;
}
