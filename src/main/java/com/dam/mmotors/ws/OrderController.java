package com.dam.mmotors.ws;


import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.pojo.Order;
import com.dam.mmotors.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_ORDER)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        List<Order> myListOrders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(myListOrders);
    }
    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order myOrder = orderService.getOrderById(id);
        return myOrder == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myOrder);
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order myOrder) {
        orderService.createOrder(myOrder);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteOrderById(@PathVariable Long id) {
        Boolean isDelete = orderService.deleteOrder(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateOldCar(@PathVariable Long id, @RequestBody Order newOrder) {
        orderService.updatedOrder(newOrder, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
