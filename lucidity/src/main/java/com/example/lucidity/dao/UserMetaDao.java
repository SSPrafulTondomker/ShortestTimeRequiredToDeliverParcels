package com.example.lucidity.dao;


import com.example.lucidity.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMetaDao {
    List<User> users;
    List<Restaurant> restaurants;
    List<Consignment> consignments;

    UserMetaDao() {
        users = new ArrayList<>();
        restaurants = new ArrayList<>();
        consignments = new ArrayList<>();
    }


    public List<Consignment> getConsignment(){
        User user = this.getSender();
        Restaurant restaurant1 = new Restaurant("restaurant1", new Location(2, 2), "r1");
        Restaurant restaurant2 = new Restaurant("restaurant2", new Location(4, 4), "r2");
        User c1 = new User("c1", new Location(6, 6), "c1");
        User c2 = new User("c2", new Location(8, 8), "c2");

        this.consignments.add(new Consignment(user, restaurant1, c1, "cng1"));
        this.consignments.add(new Consignment(user, restaurant2, c2, "cng2"));

        return consignments;
    }

    public User getSender() {
        return  new User("Batman", new Location(0, 0), "s1");

    }

}


