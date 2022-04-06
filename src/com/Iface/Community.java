package com.Iface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Community {
    String name;
    String description;
    String owner;

    //List
    ArrayList<String> members = new ArrayList<String>();
    Map<String, ArrayList<String>> msg = new HashMap<String, ArrayList<String>>();

    public Community(){

    }

    public Community(String name, String description, String owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public Map<String, ArrayList<String>> getMsg() {
        return msg;
    }
}