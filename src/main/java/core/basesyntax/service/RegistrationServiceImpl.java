package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN = 6;
    private static final int MIN_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("Login, password or age can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN) {
            throw new RegistrationException("Not valid login " + user.getLogin()
                    + " Min allowed login is " + MIN_LOGIN);
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new RegistrationException("Not valid password " + user.getPassword()
                    + "Min allowed password is " + MIN_PASSWORD);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User " + user.getLogin()
                    + " already exist in storage.");
        }
        return storageDao.add(user);
    }
}
