package com.Iface;

import java.util.ArrayList;

public class Feed {
    ArrayList<String> login = new ArrayList<>();
    ArrayList<String> scrap = new ArrayList<>();
    ArrayList<Boolean> isPrivate = new ArrayList<>();

    public ArrayList<String> getLogin() {
        return login;
    }

    public void setLogin(ArrayList<String> login) {
        this.login = login;
    }

    public ArrayList<String> getScrap() {
        return scrap;
    }

    public void setScrap(ArrayList<String> scrap) {
        this.scrap = scrap;
    }

    public ArrayList<Boolean> getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(ArrayList<Boolean> isPrivate) {
        this.isPrivate = isPrivate;
    }
}
