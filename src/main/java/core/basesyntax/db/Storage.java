package core.basesyntax.db;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<User> people = new ArrayList<>();

    public static String getLogin(String userLogin) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getLogin().equals(userLogin)) {
                return people.get(i).getLogin();
            }
        }
        return null;
    }
}

