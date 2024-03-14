package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class StorageDaoImpl implements StorageDao {
    private static final AtomicLong index = new AtomicLong(0);

    @Override
    public User add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        long id = index.incrementAndGet();
        user.setId(id);
        synchronized (Storage.people) {
            Storage.people.add(user);
        }
        return user;
    }

    @Override
    public Optional<User> get(String login) {
        synchronized (Storage.people) {
            for (User user : Storage.people) {
                if (user.getLogin().equals(login)) {
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }
}
