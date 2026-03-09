package com.dam.mmotors.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    public Integer getPrice() {
        if(hasTradeIn()){
            return price - takeOldCar.getPrice();
        }
        return price;
    }

    @OneToOne(optional = true)
    @JoinColumn(name = "oldCar_id")
    @JsonManagedReference
    private OldCar takeOldCar;

    public Boolean hasTradeIn() {
        return takeOldCar != null;
    }
}
