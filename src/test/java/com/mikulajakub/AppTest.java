package com.mikulajakub;

import com.mikulajakub.model.Citizen;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    public void testStreamsDistinctByPropertyV3() {

        List<Citizen> citizens = Arrays.asList(
                new Citizen("Kuba", 50),
                new Citizen("Blaz", 40),
                new Citizen("Emilka", 20),
                new Citizen("Kuba", 51)
        );

        System.out.println("Before: " + citizens);

        citizens = citizens.stream()
                        .filter(distinctByProperty())
                                .collect(Collectors.toList());

        System.out.println("After: " + citizens);

        assertThat(citizens).hasSize(3);
    }

    private static Predicate<Citizen> distinctByProperty() {
        Set<String> keys = new HashSet<>();
        return x -> keys.add(x.getName());
    }


}
