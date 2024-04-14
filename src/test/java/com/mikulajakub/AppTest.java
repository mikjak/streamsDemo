package com.mikulajakub;

import com.mikulajakub.model.Citizen;
import com.mikulajakub.model.Movie;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    private static <T, K> Predicate<T> distinctByPropertyV5(
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

    @Test
    public void streamsAnyMatch() {

        int[] numbers = {0, 2, 4, 6, 8, 10};

        boolean anyOdd = Arrays.stream(numbers)
                .anyMatch(x -> x % 2 != 0);

        assertThat(anyOdd).isFalse();

    }

    @Test
    public void streamsSumListElementsV1() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5);

        int sum = numbers.stream()
                .collect(Collectors.summingInt(x -> x));

        assertThat(sum).isEqualTo(15);
    }


    @Test
    public void streamsSumEvenListElementsV1() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5);

        int sum = numbers.stream()
                .mapToInt(x -> x % 2 == 0 ? x : 0)
                .sum();

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void streamsSumEvenListElementsV2() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6);

        ToIntFunction<Integer> even = x -> x % 2 == 0 ? x : 0;

        int sum = numbers.stream()
                .mapToInt(even)
                .sum();

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void streamsSumListElements() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3);

        int sum = numbers.stream()
                .reduce(0, (x, y) -> x + y);

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void filterNamesWithSV1() {

        List<String> polishNames = Arrays.asList(
                "Anna", "Jan", "Katarzyna", "Piotr", "Małgorzata",
                "Andrzej", "Agnieszka", "Tomasz", "Ewa", "Marcin",
                "Joanna", "Krzysztof", "Magdalena", "Michał", "Beata",
                "Robert", "Dorota", "Marek", "Monika", "Łukasz", "Sebastian",
                "sebastian"
        );

        long count = polishNames.stream()
                .map(String::toLowerCase)
                .filter(x -> x.contains("s"))
                .count();

        assertThat(count).isEqualTo(6L);
    }

    @Test
    public void filterNamesWithSV2() {

        List<String> polishNames = Arrays.asList(
                "Anna", "Jan", "Katarzyna", "Piotr", "Małgorzata",
                "Andrzej", "Agnieszka", "Tomasz", "Ewa", "Marcin",
                "Joanna", "Krzysztof", "Magdalena", "Michał", "Beata",
                "Robert", "Dorota", "Marek", "Monika", "Łukasz", "Sebastian",
                "sebastian"
        );

        Predicate<String> withS = x -> x.toLowerCase().contains("s");

        long count = polishNames.stream()
                .filter(withS)
                .count();

        assertThat(count).isEqualTo(6L);
    }

    @Test
    public void findAnyVsFindFirst() {

        List<String> names = Arrays.asList(
                "Anna", "Jan", "Katarzyna", "Piotr", "Małgorzata",
                "Andrzej", "Agnieszka", "Tomasz", "Ewa", "Marcin",
                "Joanna", "Krzysztof", "Magdalena", "Michał", "Beata",
                "Robert", "Dorota", "Marek", "Monika", "Łukasz"
        );

        String name1 = names.stream()
                .parallel()
                .sorted()
                .findAny()
                .get();

        String name2 = names.stream()
                .parallel()
                .sorted()
                .findFirst()
                .get();

        System.out.println("FindAny: " + name1);
        System.out.println("FindFirst: " + name2);
    }

    @Test
    public void flatMapInteger2DArray() {

        // given
        Integer[][] twoDimensionalArray = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // when
        Integer[] result = Arrays.stream(twoDimensionalArray)
                .flatMap(Arrays::stream)
                .toArray(Integer[]::new);


        Integer[] flatArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        System.out.println(Arrays.toString(result));
        System.out.println(Arrays.toString(flatArray));


        // then
        assertThat(result).isEqualTo(flatArray);
    }

    @Test
    public void flatMapIntArray() {

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
        };

        System.out.println("matrix: ");
        for(int[] a : matrix) {
            System.out.println(Arrays.toString(a));
        }

        Arrays.stream(matrix)
                .flatMap(x -> Arrays.stream(x).boxed())
                .forEach(System.out::print);

    }

    @Test
    public void matrix2DintoArray() {

        String[][] names = {
                {"Anna", "Jan", "Katarzyna", "Piotr", "Małgorzata"},
                {"Andrzej", "Agnieszka", "Tomasz", "Ewa", "Marcin"}
        };

        // get stream
        Stream<String[]> stream = Arrays.stream(names);

        // flat stream
        Stream<String> stringStream = stream
                .flatMap(Arrays::stream);

        // to array
        String[] singleArray = stringStream
                .toArray(x -> new String[x]);

        System.out.println(Arrays.toString(singleArray));
    }

    @Test
    public void testWithMovieAndActors() {
        Movie[] movies = {
                new Movie("Killer"),
                new Movie("Psy"),
                new Movie("Akademia Pana Kleksa")
        };

        movies[0].addActor("Cezary Pazura");
        movies[0].addActor("Rewiński");

        movies[1].addActor("Bogusław Linda");
        movies[1].addActor("Cezary Pazura");

        movies[2].addActor("Piotr Fronczewski");
        movies[2].addActor("Meluzyna");

//        for (Movie movie : movies) {
//            System.out.println(movie);
//        }

        Arrays.stream(movies).forEach(
                x -> {
                    System.out.println("Title: " + x.getTitle());
                    System.out.print("Actors: ");
                    System.out.print(String.join("; ", x.getActors()));
                    System.out.println();
                    System.out.println();
                }
        );

        String[] actors = Arrays.stream(movies)
                .map(x -> x.getActors())
                .flatMap(x -> x.stream())
                .distinct()
                .toArray(x -> new String[x]);

        System.out.println(Arrays.toString(actors));
    }

    @Test
    public void flatMapToInt() {

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
        };

        System.out.println("Matrix: ");
        for(int[] a : matrix) {
            System.out.println(Arrays.toString(a));
        }

        // Stream<int[]>
        Stream<int[]> stream = Stream.of(matrix);

        // IntStream
        IntStream intStream = stream
                .flatMapToInt(Arrays::stream);

        intStream.forEach(AppTest::print);
    }

    private static void print(int x) {
        System.out.print(x + " ");
    }

    @Test
    public void streamsForeachVsForeachordered() {

        String[] data = {
          "A", "b", "C", "d", "_", "E", "f", "G"
        };

        System.out.println("Parallel context - ForEach");

        for(int i = 0; i < 3; i++) {

            Arrays.stream(data)
                    .parallel()
                    .forEach(x -> print(x));

            System.out.println();
        }

        System.out.println("\n");

        System.out.println("Parallel context - ForEachOrdered");
        for (int i = 0; i < 3; i++) {
            Arrays.stream(data)
                    .parallel()
                    .forEachOrdered(AppTest::print);

            System.out.println();
        }


    }

    private static void print(String s) {
        System.out.print(s + " ");
    }

    @Test
    public void streamsGenerateInJava() {

        List<String> uuids = Stream.generate(UUID::randomUUID)
                .limit(5)
                .map(x -> x.toString()
                )
                .collect(Collectors.toList());

        System.out.println("UUIDs: ");
        uuids.forEach(System.out::println);
    }

    @Test
    public void StreamsIterate() {
        Stream<Integer> stream = Stream.iterate(10, x -> x -2)
                .limit(10);

        stream.forEach(x -> System.out.println(x));
    }

    @Test
    public void StreamsMap() {
        List<String> names = Arrays.asList(
                "Anna", "Jan", "Katarzyna", "Piotr", "Małgorzata",
                "Andrzej", "Agnieszka", "Tomasz", "Ewa", "Marcin",
                "Joanna", "Krzysztof", "Magdalena", "Michał", "Beata",
                "Robert", "Dorota", "Marek", "Monika", "Łukasz"
        );

        names.stream()
                .map(x -> x.toLowerCase().contains("j") ? x + " <- j" : x)
                .forEach(System.out::println);

    }
}
