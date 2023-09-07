package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;

    @Override
    public User add(User user) {
        user.setId(++index);
        Storage.PEOPLE.add(user);
        return user;
    }

    @Override
    public User get(String login) {
        for (User user : Storage.PEOPLE) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
