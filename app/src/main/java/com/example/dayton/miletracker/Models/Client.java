package com.example.dayton.miletracker.Models;

/**
 * Created by Dayton on 5/20/2017.
 */

public class Client {
    private int Id;
    private String Name;
    private String Address;
    private int IsDeleted;

    public Client(String name, String address) {
        Name = name;
        Address = address;
        IsDeleted = 0;
    }

    public Client(int id, String name, String address, int isDeleted) {
        Id = id;
        Name = name;
        Address = address;
        IsDeleted = isDeleted;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public int getIsDeleted() {
        return IsDeleted;
    }

    public int getId() { return Id; }
}
