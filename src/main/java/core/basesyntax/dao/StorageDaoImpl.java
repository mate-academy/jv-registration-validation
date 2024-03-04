package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;

public class StorageDaoImpl implements StorageDao {
    @Override
    public void add(User user) {
        Storage.people.add(user);
    }

    @Override
    public List<User> getAll() {
        return Storage.people;
    }
}
