package core.basesyntax.db;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<User> people = new ArrayList<>();

    public static void add(User person) {
        people.add(person);
    }

    public static List<User> getAll() {
        return people;
    }
}
