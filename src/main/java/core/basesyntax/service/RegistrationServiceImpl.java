package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRE_PASSWORD_MIN_LENGTH = 6;
    private static final int REQUIRE_MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("Sorry, this login already exit");
            }
        }
        if (user.getAge() < REQUIRE_MIN_AGE) {
            throw new RuntimeException("Sorry, age should be the same 18 y. old.");
        }
        if (user.getPassword().length() < REQUIRE_PASSWORD_MIN_LENGTH) {
            throw new RuntimeException(
                    "Sorry, your password is too small." + user.getPassword().length());
        }
        return storageDao.add(user);
    }
}
