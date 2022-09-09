package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_REQUIRE_AGE = 18;
    private static final int MIN_REQUIRE_LENGTH_PASS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Fields can not be null. Check your login, "
                    + "password and age");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login is empty");
        }
        if (user.getLogin().length() < MIN_REQUIRE_LENGTH_PASS) {
            throw new RuntimeException("Password contains less than six symbol");
        }
        if (user.getAge() < MIN_REQUIRE_AGE) {
            throw new RuntimeException("User age is least 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login: " + user.getLogin() + " - exist in base");
        }
        return storageDao.add(user);
    }
}
