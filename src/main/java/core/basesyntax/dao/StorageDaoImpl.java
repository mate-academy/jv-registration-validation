package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;

    @Override
    public User add(User user) {
        user.setId(++index);
        Storage.people.add(user);
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        return Storage.people.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }
}
