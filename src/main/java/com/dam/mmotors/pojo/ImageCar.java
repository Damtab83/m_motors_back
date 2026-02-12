package com.dam.mmotors.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ImageCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageCar_id;

    private String name;
    private Integer size;

}
