package com.dam.mmotors.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OldCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oldCar_id;

    private String brand;
    private Integer price;

//    @OneToOne(mappedBy = "takeOldCar")
//    @JsonBackReference
//    private BuyCar buyCar;

}
