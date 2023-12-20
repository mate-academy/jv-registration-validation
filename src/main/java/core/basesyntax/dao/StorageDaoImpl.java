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
    public User get(String login) {
        for (User user : Storage.people) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User get(Long id) {
        for (User user : Storage.people) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

}
