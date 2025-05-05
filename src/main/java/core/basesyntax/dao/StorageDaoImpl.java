package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;
    private static List<User> users = Storage.people;

    @Override
    public User add(User user) {
        user.setId(++index);
        users.add(user);
        user.setId((long) users.indexOf(user));
        return user;
    }

    @Override
    public User get(String login) {
        for (User user : Storage.people) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
