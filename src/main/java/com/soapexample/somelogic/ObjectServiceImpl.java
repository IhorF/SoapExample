package com.soapexample.somelogic;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 28.01.2018.
 */
@Component
public class ObjectServiceImpl implements ObjectService {
    List<BasketballClub> basketballClubs = new ArrayList<>();

    @PostConstruct
    void initialize() {
        basketballClubs.add(new BasketballClub(1, "Lakers", "LA", 16));
        basketballClubs.add(new BasketballClub(2, "Celtics", "Boston", 17));
        basketballClubs.add(new BasketballClub(3, "Bulls", "Chicago", 6));
        basketballClubs.add(new BasketballClub(4, "Heat", "Miami", 3));
        basketballClubs.add(new BasketballClub(5, "Rockets", "Houston", 2));
        basketballClubs.add(new BasketballClub(6, "Warriors", "Oakland", 3));
    }

    @Override
    public File getFileByID(int id) {
        return null;
    }

    @Override
    public File getObjectsInCSVFile() {
        File file = new File("response.csv");
        boolean exceptionOccured = false;

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file)));

            writer.write(basketballClubs.stream()
                    .sorted(Comparator.comparingInt(BasketballClub::getChampionCount))
                    .map(club -> club + "\n")
                    .collect(Collectors.joining()));
            writer.close();
        } catch (IOException e) {
            exceptionOccured = true;
        }

        return exceptionOccured ? null : file;
    }

    class BasketballClub {
        int id;
        String name;
        String city;
        int championCount;

        public BasketballClub(int id, String name, String city, int championCount) {
            this.id = id;
            this.name = name;
            this.city = city;
            this.championCount = championCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getChampionCount() {
            return championCount;
        }

        public void setChampionCount(int championCount) {
            this.championCount = championCount;
        }

        @Override
        public String toString() {
            return id + "," + name + "," + city + "," + championCount;
        }
    }
}
