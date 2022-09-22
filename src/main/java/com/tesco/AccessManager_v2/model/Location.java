package com.tesco.AccessManager_v2.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    public String id;
    public String name;
    public ArrayList<String> types;
    public CountryModel country;
    public LifecycleModel lifecycle;
}
