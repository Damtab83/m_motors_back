package com.dam.mmotors.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TestDriving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testDriving_id;

    private LocalDateTime testDate;
    private Boolean confirmed;
}
