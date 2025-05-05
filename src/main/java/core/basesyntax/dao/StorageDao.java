package core.basesyntax.dao;

import core.basesyntax.model.User;
import java.util.Optional;

public interface StorageDao {
    User add(User user);

    Optional<User> get(String login);
}
