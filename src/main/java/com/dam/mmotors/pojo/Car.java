package com.dam.mmotors.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long car_id;

    private String brand;
    private String model;
    private String motorization;
    private Integer kilometer;
    private Boolean funding;
    private Integer price;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User customer;

//    @OneToOne(optional = true)
//    @JoinColumn(name = "buyCar_id")
//    private BuyCar buy;
//
//    @OneToOne(optional = true)
//    @JoinColumn(name = "rentalCar")
//    private RentalCar rental;
//
//    public boolean isBuy() {
//        return buy != null;
//    }
//    public boolean isRental() {
//        return rental != null;
//    }

    @OneToMany
    @JoinColumn(name = "imageCar_id")
    private List<ImageCar>  images;

    @OneToOne(optional = true)
    @JoinColumn(name = "testDriving_id")
    private TestDriving testing;

//    @Transient
//    public Integer getPrice() {
//        if (buy != null) {
//            return buy.getPrice();
//        }
//        if (rental != null) {
//            return rental.getPrice();
//        }
//        return null;
//    }
}
