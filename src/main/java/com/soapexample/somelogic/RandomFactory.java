package com.soapexample.somelogic;

import com.soapexample.generated.SomeRandomObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 28.01.2018.
 *
 * Used to return random {@link String} or {@link Integer}
 * Deprecated since 1.0.1
 *
 * @version 1.0.0
 */
@Deprecated
@Component
public class RandomFactory {

    private Random random;

    @PostConstruct
    void initialize() {
        random = new Random();
    }

    public SomeRandomObject getSomeRandomObject() {
        SomeRandomObject randomObject = new SomeRandomObject();

        randomObject.setRandomInt(random.nextInt(100));
        randomObject.setRandomString(getRandomIntegers(10)
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining()));

        return randomObject;
    }

    public int getRandomInteger(int maxNumber) {
        return random.nextInt(maxNumber);
    }

    private List<Integer> getRandomIntegers(int number) {
        List<Integer> integers = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            integers.add(random.nextInt(100) + 10);
        }

        return integers;
    }
}
