package core.basesyntax.dao;

import core.basesyntax.model.User;

public interface StorageDao {
    User add(User user);

    User get(String login);

    User getById(long id);

    void clear();
}
