package com.dam.mmotors.services;

import com.dam.mmotors.pojo.Order;
import com.dam.mmotors.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {return orderRepository.findAll();}

    public Order getOrderById(Long id) {return orderRepository.findById(id).orElse(null);}

    public void createOrder(Order myOrder) { orderRepository.save(myOrder);}

    public Boolean deleteOrder(Long id) {
        Boolean toDelete = orderRepository.existsById(id);
        if (toDelete) {
            orderRepository.deleteById(id);
        }
        return toDelete;
    }

    public void updatedOrder(Order newOrder, Long id) {
        Order orderExisting = this.getOrderById(id);
        if(orderExisting != null) {
            orderExisting.setCar(newOrder.getCar());
            orderExisting.setOfferType(newOrder.getOfferType());
            orderExisting.setOldCar(newOrder.getOldCar());
            orderExisting.setSubscription(newOrder.getSubscription());
        }
    }
}
