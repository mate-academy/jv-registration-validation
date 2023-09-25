package core.basesyntax.test.dao;

import core.basesyntax.test.model.User;

public interface StorageDao {
    User add(User user);

    User get(String login);
}
