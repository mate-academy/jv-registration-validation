package core.basesyntax.dao;

import core.basesyntax.model.User;
import java.util.List;

public interface StorageDao {
    void add(User user);

    List<User> getAll();
}