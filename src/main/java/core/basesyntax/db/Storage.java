package core.basesyntax.db;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<User> people = new ArrayList<>();

    public static boolean checkLoginExist(String userLogin) {
        for (int i = 0; i < Storage.people.size(); i++) {
            if (Storage.people.get(i).getLogin().equals(userLogin)) {
                return true;
            }
        }
        return false;
    }
}

