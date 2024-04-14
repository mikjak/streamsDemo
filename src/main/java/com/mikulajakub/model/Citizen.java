package com.mikulajakub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Citizen {
    private String name;
    private int age;

    @Override
    public String toString() {
        return String.format("%s: %d", name, age);
    }
}
