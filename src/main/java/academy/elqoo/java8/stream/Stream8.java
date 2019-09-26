package academy.elqoo.java8.stream;


import java.lang.UnsupportedOperationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@SuppressWarnings("WeakerAccess")
public class Stream8 {

    public static List<Integer> returnSquareRoot(List<Integer> numbers){
        return numbers.stream().map(x -> (int) Math.sqrt(x)).collect(Collectors.toList());
    }

    public static List<Integer> getAgeFromUsers(List<User> user){
        return user.stream().map(User::getAge).collect(Collectors.toList());
    }

    public static List<Integer> getDistinctAges(List<User> users){
        return users.stream().map(User::getAge).distinct().collect(Collectors.toList());
    }

    public static List<User> getLimitedUserList(List<User> users, int limit){
        return users.stream().limit(limit).collect(Collectors.toList());
    }

    public static Integer countUsersOlderThen25(List<User> users){
        return (int) users.stream()
                .filter(x -> x.getAge() > 25)
                .count();
    }

    public static List<String> mapToUpperCase(List<String> strings){
        return strings.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    public static Integer sum(List<Integer> integers){
        return integers.stream().reduce(0, Integer::sum);
    }

    public static List<Integer> skip(List<Integer> integers, Integer toSkip){
        return integers.stream().skip(toSkip).collect(Collectors.toList());
    }

    public static List<String> getFirstNames(List<String> names){
        return names.stream().map(x -> x.split(" ")[0]).collect(Collectors.toList());
    }

    public static List<String> getDistinctLetters(List<String> names){
        return names.stream()
                .flatMapToInt(String::chars)
                .mapToObj(i -> (char) i)
                .map(Object::toString)
                .collect(Collectors.toList());
    }


    public static String separateNamesByComma(List<User> users){
        final StringBuilder result = users.stream()
                .map(User::getName)
                .reduce(new StringBuilder(),
                        (stringBuilder, str) -> stringBuilder.append(str).append(", "),
                        StringBuilder::append);
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }

    public static double getAverageAge(List<User> users){
        return users.stream().mapToInt(User::getAge).average().orElse(-1.0);
    }

    public static Integer getMaxAge(List<User> users){
        return users.stream().mapToInt(User::getAge).max().orElse(-1);
    }

    public static Integer getMinAge(List<User> users) {
        return users.stream().mapToInt(User::getAge).min().orElse(-1);
    }

    public static Map<Boolean, List<User>> partionUsersByGender(List<User> users){
        return users.stream()
                .collect(Collectors.toMap(
                        User::isMale,
                        (User user) -> {
                            List<User> list = new ArrayList<User>();
                            list.add(user);
                            return list;
                        },
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }
                ));
    }

    public static Map<Integer, List<User>> groupByAge(List<User> users){
        return users.stream().collect(Collectors.toMap(
                User::getAge,
                user -> {
                    List<User> list = new ArrayList<User>();
                    list.add(user);
                    return list;
                },
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                }
        ));
    }

    public static Map<Boolean, Map<Integer, List<User>>> groupByGenderAndAge(List<User> users){
        HashMap<Boolean, Map<Integer, List<User>>> map = new HashMap<>();

        Function<User, List<User>> function = user -> {
            List<User> list = new ArrayList<>();
            list.add(user);
            return list;
        };

        BinaryOperator<List<User>> binaryOperator = (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };

        final Map<Integer, List<User>> boys = users.stream()
                .filter(User::isMale)
                .collect(Collectors.toMap(User::getAge, function, binaryOperator));

        final Map<Integer, List<User>> girls = users.stream()
                .filter(u -> !u.isMale())
                .collect(Collectors.toMap(User::getAge, function, binaryOperator));

        map.put(true, boys);
        map.put(false, girls);

        return map;
    }

    public static Map<Boolean, Long> countGender(List<User> users){
        return users.stream().collect(Collectors.toMap(
                User::isMale,
                user -> 1L,
                Long::sum
        ));
    }

    public static boolean anyMatch(List<User> users, int age){
        return users.stream().anyMatch(user -> user.getAge().equals(age));
    }

    public static boolean noneMatch(List<User> users, int age){
        return users.stream().noneMatch(user -> user.getAge().equals(age));
    }

    public static Optional<User> findAny(List<User> users, String name){
        return users.stream().filter(u -> u.getName().equals(name)).findAny();
    }

    public static List<User> sortByAge(List<User> users){
        return users.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
    }

    public static Stream<Integer> getBoxedStream(IntStream stream){
        return stream.boxed();
    }

    public static List<Integer> generateFirst10PrimeNumbers(){
        return Stream.iterate(2, x -> ++x)
                .filter(Stream8::isPrime)
                .limit(10)
                .collect(Collectors.toList());

    }

    public static boolean isPrime(int number) {
        return IntStream.rangeClosed(2, number/2).noneMatch(i -> number%i == 0);
    }

    public static List<Integer> generate10RandomNumbers(){
        return new Random().ints()
                .limit(10)
                .boxed()
                .collect(Collectors.toList());
    }

    public static User findOldest(List<User> users){
        return users.stream()
                .max(Comparator.comparing(User::getAge))
                .orElse(null);
    }

    public static int sumAge(List<User> users){
        return users.stream().mapToInt(User::getAge).sum();
    }

    public static IntSummaryStatistics ageSummaryStatistics(List<User> users){
        return users.stream().mapToInt(User::getAge).summaryStatistics();
    }

}
