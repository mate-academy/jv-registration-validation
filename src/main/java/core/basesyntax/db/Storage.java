package core.basesyntax.db;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<User> people = new ArrayList<>();

    public static List<User> getPeople() {
        return people;
    }

    public static void resetPeopleStorage() {
        people = new ArrayList<>();
    }
}
