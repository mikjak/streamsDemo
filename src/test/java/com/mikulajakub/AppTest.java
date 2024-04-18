package com.mikulajakub;

import com.mikulajakub.model.Citizen;
import com.mikulajakub.model.Movie;
import org.assertj.core.data.MapEntry;
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
        for (int[] a : matrix) {
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
        for (int[] a : matrix) {
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

        for (int i = 0; i < 3; i++) {

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
        Stream<Integer> stream = Stream.iterate(10, x -> x - 2)
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

    @Test
    public void streamsMax() {
        List<String> list = Arrays.asList("A", "B", "DC", "D");

        System.out.println(list);

        String max = list.stream()
                .max((x, y) -> x.compareTo(y))
                .get();

        System.out.println(max);

    }

    @Test
    public void streamsPeek() {
        List<String> characters = Arrays.asList(
                "Eddard \"Ned\" Stark",
                "Robert Baratheon",
                "Jaime Lannister",
                "Catelyn Stark",
                "Cersei Lannister",
                "Daenerys Targaryen",
                "Jorah Mormont",
                "Jon Snow",
                "Robb Stark",
                "Sansa Stark",
                "Arya Stark",
                "Tyrion Lannister",
                "Khal Drogo",
                "Petyr \"Littlefinger\" Baelish",
                "Davos Seaworth",
                "Stannis Baratheon",
                "Melisandre",
                "Bronn",
                "Varys",
                "Shae",
                "Margaery Tyrell",
                "Tywin Lannister",
                "Talisa Maegyr",
                "Ygritte",
                "Gendry",
                "Tormund Giantsbane",
                "Brienne of Tarth",
                "Ramsay Bolton",
                "Gilly",
                "Daario Naharis",
                "Missandei",
                "Tommen Baratheon",
                "Ellaria Sand",
                "Jaqen H'ghar",
                "Roose Bolton",
                "The High Sparrow",
                "Grey Worm"
        );

        characters.stream()
                .filter(character -> character.contains("Lannister"))
                .peek(x -> System.out.println(x + " - after filter"))
                .map(String::toUpperCase)
                .peek(x -> System.out.println(x + " - after map"))
                .count();
    }

    @Test
    public void streamsReduce() {

        int[] numbers = {4, 3, 6, 1};

        for (int i : numbers) {
            System.out.print(i + " ");
        }

        System.out.println();

        int product = Arrays.stream(numbers)
                .reduce(1, (a, b) -> a * b);

        int sum = Arrays.stream(numbers)
                .reduce(0, Integer::sum);

        System.out.println("product: " + product);
        System.out.println("sum: " + sum);

    }

    @Test
    public void streamsSortedHashMap() {

        Map<String, Integer> dict = new HashMap<>();
        dict.put("John", 20);
        dict.put("Jane", 25);
        dict.put("Jack", 30);
        dict.put("George", 40);
        dict.put("Mark", 50);
        dict.put("David", 60);
        dict.put("Beck", 30);

        System.out.println("HashMap: ");
        dict.forEach(
                (x, y) -> System.out.printf("%s - %s\n", x, y)
        );

        System.out.println();
        System.out.println("Sorted values:");

        dict.entrySet().stream()
                .sorted(Comparator.comparing(
                        Map.Entry::getValue
                ))
                .forEach(x -> System.out.printf("%s - %s\n", x.getKey(), x.getValue()));
    }

    @Test
    public void streamsSortedList() {
        List<Citizen> citizens = Arrays.asList(
                new Citizen("Ethan", 23),
                new Citizen("Arturo", 52),
                new Citizen("Juan", 18),
                new Citizen("Alicia", 42),
                new Citizen("Emilia", 17),
                new Citizen("Kubek", 50)
        );

        System.out.println("People: ");
        citizens.forEach(System.out::println);

        System.out.println();
        System.out.println("People sorted by name: ");

        citizens.stream()
                .sorted(Comparator.comparing(Citizen::getName))
//                .sorted((x, y) -> x.getName().compareTo(y.getName()))
                .forEach(System.out::println);


    }

    @Test
    public void streamsReversedSortedList() {
        List<Citizen> citizens = Arrays.asList(
                new Citizen("Ethan", 23),
                new Citizen("Arturo", 52),
                new Citizen("Juan", 18),
                new Citizen("Alicia", 42),
                new Citizen("Emilia", 17),
                new Citizen("Kubek", 50)
        );

        System.out.println("People: ");
        citizens.forEach(System.out::println);

        System.out.println();
        System.out.println("People sorted by name: ");

        citizens.stream()
                .sorted((first, second) -> second.getName().compareTo(first.getName()))
                .forEach(System.out::println);
    }

    @Test
    public void streamsMapToInt() {
        List<Citizen> citizens = Arrays.asList(
                new Citizen("Ethan", 23),
                new Citizen("Arturo", 52),
                new Citizen("Juan", 18),
                new Citizen("Alicia", 42),
                new Citizen("Emilia", 17),
                new Citizen("Kubek", 50)
        );

        System.out.println("People: ");
        citizens.forEach(System.out::println);

        int sumAges = citizens.stream()
                .mapToInt(p -> p.getAge())
                .sum();

        int maxAge = citizens.stream()
                .mapToInt(Citizen::getAge)
                .max().getAsInt();

        System.out.println("Sum ages: " + sumAges);
        System.out.println("Max ages: " + maxAge);
    }

    @Test
    public void streamsFindFirstOrElse() {
        List<String> names = List.of(
                "Alice", "Bob", "Charlie", "David", "Eva",
                "Frank", "Grace", "Hannah", "Isaac", "Julia",
                "Kevin", "Lena", "Michael", "Nina", "Oscar",
                "Paul", "Quinn", "Rachel", "Samuel", "Tina",
                "Ursula", "Victor", "Wendy", "Xander", "Yara",
                "Zane", "Abby", "Ben", "Chloe", "Daniel"
        );

        String name = names.stream()
                .filter(n -> n.startsWith("C"))
                .findFirst().orElse("Not found !!!");

        System.out.println(name);
    }

    @Test
    public void streamsFindFirstIfPresent() {
        List<String> names = List.of(
                "Alice", "Bob", "Charlie", "David", "Eva",
                "Frank", "Grace", "Hannah", "Isaac", "Julia",
                "Kevin", "Lena", "Michael", "Nina", "Oscar",
                "Paul", "Quinn", "Rachel", "Samuel", "Tina",
                "Ursula", "Victor", "Wendy", "Xander", "Yara",
                "Zane", "Abby", "Ben", "Chloe", "Daniel"
        );

        names.stream()
                .filter(n -> n.startsWith("C"))
                .findFirst().ifPresent(x -> System.out.println("Hello " + x));

    }

    @Test
    public void streamsCollectorsJoinStringV1() {

        List<String> names = List.of(
                "James", "John", "Robert"
        );

        String namesJoined = names.stream()
                .collect(Collectors.joining(" - ")
                );

        System.out.println(namesJoined);
    }

    @Test
    public void test001() {
        Stream<String> names = Stream.of("John", "Marry", "George", "Paul", "Alice", "Ann");

        names
                .filter(n -> n.contains("a"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(x -> System.out.printf("%s, ", x));

    }

    @Test
    public void test002() {
        Stream<String> names = Stream.of("John", "Marry", "George", "Paul", "Alice", "Ann");

        names
                .filter(
                        e -> {
                            System.out.println("filter: " + e);
                            return true;
                        })
                .forEach(e -> System.out.println("forEach: " + e));

    }

    @Test
    public void test003() {
        final List<List<Integer>> slicedIntegers = Arrays.asList(
                Arrays.asList(2, 4, 6),
                Arrays.asList(8, 10, 12),
                Arrays.asList(14, 16)
        );

        final List<Integer> simpleIntegerList = slicedIntegers
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println("slicedIntegers: " + slicedIntegers);
        System.out.println("simpleIntegerList: " + simpleIntegerList);
    }
}
