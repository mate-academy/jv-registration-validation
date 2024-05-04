package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class StorageDaoImpl implements StorageDao {
    private static Long index = 0L;

    @Override
    public User add(User user) {
        if (user == null || user.getAge() == null
                || user.getPassword() == null || user.getLogin() == null) {
            throw new InvalidInputDataException("Data cannot be null");
        }
        user.setId(++index);
        Storage.people.add(user);
        return user;
    }

    @Override
    public User get(String login) {
        if (login == null) {
            throw new InvalidInputDataException("Login cannot be null");
        }
        for (User user : Storage.people) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
