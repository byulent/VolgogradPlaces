package ru.byulent.volgogradplaces.entities;

public class User {
    private static boolean authorized = false;

    public static void authorize (){
        authorized = true;
    }

    public static boolean isAuthorized() {
        return authorized;
    }
}
