package core.basesyntax.dao;

import static core.basesyntax.db.Storage.people;

import core.basesyntax.model.User;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;

    @Override
    public User add(User user) {
        user.setId(++index);
        people.add(user);
        return user;
    }

    @Override
    public User get(String login) {
        for (User user : people) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        people.clear();
    }
}
