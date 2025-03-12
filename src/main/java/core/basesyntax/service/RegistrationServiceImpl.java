package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int minLetters = 6;
    private final int minAge = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            return null;
        }

        if (user.getAge() == null) {
            throw new NullPointerException("null Age");
        }

        if (user.getPassword() == null) {
            throw new NullPointerException("null Pass");
        }

        if (user.getLogin() == null) {
            throw new NullPointerException("null Login");
        }

        if (storageDao.get(user.getLogin()) == null && user.getLogin().length() >= minLetters
        && user.getPassword().length() >= minLetters && user.getAge() >= minAge) {
            storageDao.add(user);
        }
        return null;
    }
}
