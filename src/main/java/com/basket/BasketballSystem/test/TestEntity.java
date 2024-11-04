package com.basket.BasketballSystem.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotNull(message="Name can't be null")
    public String name;
    //    @NotNull(message = "Lastname can't be null")
    public String lastname;

    public boolean isDeveloper;

    @Override
    public String toString() {
        return  "id: " + id  + " name: " + name;
    }
}

