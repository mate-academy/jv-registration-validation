package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        final int minLoginLength = 6;
        final int minPasswordLength = 6;
        final int minAge = 18;

        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null");
        }

        if (user.getLogin().length() < minLoginLength) {
            throw new RegistrationException(
                "Login is less than " 
                + minLoginLength 
                + " characters"
            );
        }
        
        if (user.getPassword() == null || user.getPassword().length() < minPasswordLength) {
            throw new RegistrationException(
                "Password is less than " 
                + minPasswordLength 
                + " characters"
            );
        }

        if (user.getAge() == null || user.getAge() < minAge) {
            throw new RegistrationException(
                "Age is less than " 
                + minAge 
                + " years"
            );
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }

        return storageDao.add(user);
    }
}
