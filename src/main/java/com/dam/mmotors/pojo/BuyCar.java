package com.dam.mmotors.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BuyCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyCar_id;

    private Integer price;

    @OneToOne(optional = true)
    @JoinColumn(name = "oldCar_id")
    private OldCar takeOldCar;

    public boolean hasTradeIn() {
        return takeOldCar != null;
    }
}
