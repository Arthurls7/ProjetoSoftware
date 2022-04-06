package com.Iface;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Account> users = new ArrayList<Account>();
        ArrayList<Community> communities = new ArrayList<Community>();
        Feed posts = new Feed();
        Menu menu = new Menu();
        menu.startMenu(users, communities, posts);
    }
}