package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int limitAge = 18;
    private final int minPasswordLength = 6;

    @Override
    public User register(User user) {
        checkNullRegister(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("\n Your date is Invalid (Login is already registred)");
        }
        if (user.getAge() < limitAge) {
            throw new RuntimeException("\n Your date is Invalid (Your age is less) " + limitAge);
        }
        if (user.getPassword().length() < minPasswordLength) {
            throw new RuntimeException("\n Your date is Invalid (Your password's length less) "
                    + minPasswordLength);
        }
        storageDao.add(user);
        return user;
    }

    private void checkNullRegister(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException(" \n Your date is Invalid "
                    + "(User, User's age, User's login, User's password: can not be null (empty)");
        }
    }
}
