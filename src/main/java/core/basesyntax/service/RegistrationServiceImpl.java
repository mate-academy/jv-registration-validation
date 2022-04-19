package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    public static final int ADULT_AGE = 18;
    public static final int MAX_LENTH = 6;

    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getId() == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("Please provide valid registration data");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists");
        }

        if (user.getAge() < ADULT_AGE) {
            throw new RuntimeException("User should be 18 y. o. to register");
        }

        if (user.getPassword().length() < MAX_LENTH) {
            throw new RuntimeException("Invalid age for registration");
        }

        return storageDao.add(user);
    }
}
