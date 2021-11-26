package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;

    @Override
    public User add(User user) {
        if (user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Can`t register user, invalid data");
        }

        for (User user1 : Storage.people) {
            if (user.getLogin().equals(user1.getLogin())) {
                throw new RuntimeException("Can`t register user, such loging already exist!");
            }
        }

        if (user.getPassword().length() >= 6
                && user.getAge() >= 18
                && user.getAge() < 130
                && user.getLogin().length() >= 1) {
            Storage.people.add(user);
        } else {
            throw new RuntimeException("Can't register user, uncorrect data");
        }
        return null;
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
