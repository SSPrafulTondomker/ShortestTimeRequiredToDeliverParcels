package com.example.lucidity.services;


import com.example.lucidity.dao.*;
import com.example.lucidity.dto.*;
import com.example.lucidity.response.*;
import com.example.lucidity.services.impl.IEstimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstimationService implements IEstimationService {

    @Autowired
    UserMetaDao userMetaDao;

    int calculateDistance(Location l1, Location l2) {
        return Math.abs((l2.getX() - l1.getX()) + (l2.getY() - l2.getY()));
    }

    Map<String, List<NodeAndWeightPair>> createGraph(User sender, List<Restaurant> restaurants, List<User> receivers) {
        Map<String, List<NodeAndWeightPair>> adj = new HashMap<>();
        for (Restaurant restaurant: restaurants) {
            List<NodeAndWeightPair> tmp = adj.getOrDefault(sender.getId(), new ArrayList<>());
            tmp.add(new NodeAndWeightPair(restaurant.getId(), calculateDistance(sender.getPosition(), restaurant.getPosition())));
            adj.put(sender.getId(), tmp);
        }

        for (Restaurant restaurant: restaurants) {
            for (User receiver: receivers) {
                List<NodeAndWeightPair> tmp = adj.getOrDefault(restaurant.getId(), new ArrayList<>());
                tmp.add(new NodeAndWeightPair(receiver.getId(), calculateDistance(restaurant.getPosition(), receiver.getPosition())));
                adj.put(restaurant.getId(), tmp);

                tmp = adj.getOrDefault(receiver.getId(), new ArrayList<>());
                tmp.add(new NodeAndWeightPair(restaurant.getId(), calculateDistance(restaurant.getPosition(), receiver.getPosition())));
                adj.put(receiver.getId(), tmp);
            }
        }

        for (Restaurant restaurant1: restaurants) {
            for (Restaurant restaurant2: restaurants) {
                if (!restaurant1.getId().equals(restaurant2.getId())) {
                    List<NodeAndWeightPair> tmp = adj.getOrDefault(restaurant1.getId(), new ArrayList<>());
                    tmp.add(new NodeAndWeightPair(restaurant2.getId(), calculateDistance(restaurant1.getPosition(), restaurant2.getPosition())));
                    adj.put(restaurant1.getId(), tmp);

                    tmp = adj.getOrDefault(restaurant2.getId(), new ArrayList<>());
                    tmp.add(new NodeAndWeightPair(restaurant1.getId(), calculateDistance(restaurant2.getPosition(), restaurant1.getPosition())));
                    adj.put(restaurant2.getId(), tmp);
                }
            }
        }


        return adj;
    }

    Map<String, Integer> callDjikstra(Map<String, List<NodeAndWeightPair>> adj, User sender) {
        PriorityQueue<NodeAndWeightPair> q = new PriorityQueue<>();
        Map<String, Boolean> visited = new HashMap<>();

        q.add(new NodeAndWeightPair(sender.getId(), 0));
        visited.put(sender.getId(), true);

        Map<String, Integer> dis = new HashMap<>();
        while (!q.isEmpty()) {
            NodeAndWeightPair parent = q.poll();
            List<NodeAndWeightPair> childs = adj.getOrDefault(parent.getId(), new ArrayList<>());
            for (NodeAndWeightPair child: childs) {
                if (child.getDistance() + parent.getDistance() < dis.getOrDefault(child.getId(), Integer.MAX_VALUE - 1)) {
                    dis.put(child.getId(), child.getDistance() + parent.getDistance());
                    q.add(new NodeAndWeightPair(child.getId(), child.getDistance() + parent.getDistance()));
                    visited.put(child.getId(), true);
                }
            }
        }

        return dis;
    }

    double getShortestTimeRequiredToDelivered(List<Consignment> consignments, User sender) {
        List<Restaurant> restaurants = new ArrayList<>();
        List<User> receivers = new ArrayList<>();

        for (Consignment consignment: consignments) {
            restaurants.add(consignment.getRestaurant());
            receivers.add(consignment.getReceiver());
        }
        Map<String, List<NodeAndWeightPair>> adj = createGraph(sender, restaurants, receivers);

        Map<String, Integer> dis = callDjikstra(adj, sender);

        Double totDistance = 0.0d;
        for (User receiver: receivers) {
            totDistance += dis.getOrDefault(receiver.getId(), 0);
        }
        return totDistance/ 20.0;
    }


    @Override
    public GetShortestTimeRequiredResponse getShortestTime(){
        List<Consignment> consignments = userMetaDao.getConsignment();
        User sender = userMetaDao.getSender();
        return new GetShortestTimeRequiredResponse(getShortestTimeRequiredToDelivered(consignments, sender));
    }
}
