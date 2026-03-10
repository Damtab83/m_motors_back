package com.dam.mmotors.pojo;

import com.dam.mmotors.pojo.enumerate.OfferType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @ManyToOne
    private Car car;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    @OneToOne
    @JoinColumn(name = "oldCar_id")
    private OldCar oldCar;

    private Boolean subscription;

    public int calculateFinalPrice() {

        int price = car.getPrice();

        if (offerType == OfferType.BUY && oldCar != null) {
            price -= oldCar.getPrice();
        }

        return price;
    }
}
