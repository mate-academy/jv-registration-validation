package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;
    private List<User> people = new ArrayList<>();

    @Override
    public User add(User user) {
        user.setId(++index);
        people.add(user);
        Storage.setPeople(people);
        return user;
    }

    @Override
    public User get(String login) {
        for (User user : Storage.getPeople()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
