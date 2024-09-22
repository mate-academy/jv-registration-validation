package core.basesyntax.dao;

import core.basesyntax.service.model.User;

public interface StorageDao {
    User add(User user);

    User get(String login);
}
