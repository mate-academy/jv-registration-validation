package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (Storage.people.size() > 0) {
            for (User person : Storage.people) {
                if (Objects.equals(person.getLogin(), user.getLogin())) {
                    throw new RuntimeException("There is a user with such a login in the Storage");
                }
            }
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be at least 18 years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User password must be at least 6 characters long");
        }
        storageDao.add(user);
        return user;
    }
}
