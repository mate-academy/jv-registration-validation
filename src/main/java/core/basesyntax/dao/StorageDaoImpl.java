package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.concurrent.atomic.AtomicLong;

public class StorageDaoImpl implements StorageDao {
    private static final AtomicLong index = new AtomicLong(0);

    @Override
    public User add(User user) {
        long id = index.incrementAndGet();
        user.setId(id);
        synchronized (Storage.people) {
            Storage.people.add(user);
        }
        return user;
    }

    @Override
    public User get(String login) {
        synchronized (Storage.people) {
            for (User user : Storage.people) {
                if (user.getLogin().equals(login)) {
                    return user;
                }
            }
        }
        return null;
    }
}
