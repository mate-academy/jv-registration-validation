package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int minAge = 18;
    private final int maxAge = 106;
    private final int minPasswordLength = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!!!");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Login cannot be null!!");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationException("Password cannot be null or empty line!!!");
        }
        if (user.getPassword().length() < minPasswordLength) {
            throw new RegistrationException("Your password must contain at least "
                    + minPasswordLength + " characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("there is " + user.getLogin()
                    + " user already exist!");
        }
        if (user.getAge() != null || user.getAge() < minAge) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + minAge);
        }
        return storageDao.add(user);
    }
}
