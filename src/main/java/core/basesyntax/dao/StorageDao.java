package core.basesyntax.dao;

import core.basesyntax.InvalidDataException;
import core.basesyntax.model.User;

public interface StorageDao {
    User add(User user) throws InvalidDataException;

    User get(String login);
}
