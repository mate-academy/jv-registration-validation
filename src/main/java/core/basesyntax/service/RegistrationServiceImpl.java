package core.basesyntax.service;

import core.basesyntax.Expected.myExpectedClass;
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
            throw new myExpectedClass("User cannot be null");
        }

        if (user.getAge() == null) {
            throw new myExpectedClass("User Age cannot be null");
        }

        if (user.getPassword() == null) {
            throw new myExpectedClass("User Pass cannot be null");
        }

        if (user.getLogin() == null) {
            throw new myExpectedClass("User Login cannot be null");
        }

        if (storageDao.get(user.getLogin()) == null && user.getLogin().length() >= minLetters
            && user.getPassword().length() >= minLetters && user.getAge() >= minAge) {
            storageDao.add(user);
            return user;
        }
        return null;
    }
}
