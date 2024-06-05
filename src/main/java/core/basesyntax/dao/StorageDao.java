package core.basesyntax.dao;

import core.basesyntax.model.User;

public interface StorageDao {

    void clear();

    User add(User user);

    User get(String login);
}
