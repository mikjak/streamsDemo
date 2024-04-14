package com.mikulajakub;

import com.mikulajakub.model.Citizen;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    public void testStreamsDistinctByPropertyV1() {

        List<Citizen> citizens = Arrays.asList(
                new Citizen("Kuba", 50),
                new Citizen("Blaz", 40),
                new Citizen("Emilka", 20),
                new Citizen("Kuba", 51)
        );

        System.out.println("Before: " + citizens);

        Collection<Citizen> distinctCitizens = citizens.stream()
                        .collect(Collectors.toMap(
                                Citizen::getName,
                                Function.identity(),
                                (a, b) -> a
                        )).values();

        System.out.println("After: " + distinctCitizens);

        assertThat(distinctCitizens).hasSize(3);
    }

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
                        .filter(distinctByPropertyV3())
                                .collect(Collectors.toList());

        System.out.println("After: " + citizens);

        assertThat(citizens).hasSize(3);
    }

    @Test
    public void testStreamsDistinctByPropertyV4() {

        List<Citizen> citizens = Arrays.asList(
                new Citizen("Kuba", 50),
                new Citizen("Blaz", 40),
                new Citizen("Emilka", 20),
                new Citizen("Kuba", 51)
        );

        System.out.println("Before: " + citizens);

        citizens = citizens.stream()
                .filter(distinctByPropertyV4(Citizen::getName))
                .collect(Collectors.toList());

        System.out.println("After: " + citizens);

        assertThat(citizens).hasSize(3);
    }

    @Test
    public void testStreamsDistinctByPropertyV5() {

        List<Citizen> citizens = Arrays.asList(
                new Citizen("Kuba", 50),
                new Citizen("Blaz", 40),
                new Citizen("Emilka", 20),
                new Citizen("Kuba", 51)
        );

        System.out.println("Before: " + citizens);

        citizens = citizens.stream()
                .filter(distinctByPropertyV5(Citizen::getName))
                .collect(Collectors.toList());

        System.out.println("After: " + citizens);

        assertThat(citizens).hasSize(3);
    }

    private static Predicate<Citizen> distinctByPropertyV3() {
        Set<String> keys = new HashSet<>();
        return x -> keys.add(x.getName());
    }

    private static Predicate<Citizen> distinctByPropertyV4(
            Function<Citizen, String> keyProvider
    ) {
        Set<String> keys = new HashSet<>();
        return x -> keys.add(keyProvider.apply(x));
    }

    // T - object's type where functions applies to
    // K - object's type of function return
    private static <T,K> Predicate<T> distinctByPropertyV5(
            Function<T, K> keyProvider
    ) {
        Set<K> keys = new HashSet<>();
        return x -> keys.add(keyProvider.apply(x));
    }

    @Test
    public void streamsCountDistinctElements() {
        List<String> names =
                Arrays.asList(
                        "Kuba", "Emilka", "Jagoda", "Jaga",
                        "Kuba", "Emilka"
                );

        long count = names.stream()
                        .distinct().count();

        assertThat(count).isEqualTo(4);
    }

    @Test
    public void streamsAllMatch() {
        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        System.out.println("Numbers: " + Arrays.toString(numbers));

        boolean allEven = Arrays.stream(numbers)
                .allMatch(x -> x % 2 == 0);

        System.out.println("All numbers are even? " + allEven);

        assertThat(allEven).isFalse();
    }

}
