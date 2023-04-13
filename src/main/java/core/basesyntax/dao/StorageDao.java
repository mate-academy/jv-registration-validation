package core.basesyntax.dao;

import core.basesyntax.model.User;
import java.util.List;

public interface StorageDao {
    User add(User user);

    User get(String login);

    List<User> getUsers();
}
