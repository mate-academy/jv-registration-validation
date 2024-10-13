package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null!");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login must contain at least 6 symbols!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists!");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password must contain at least 6 symbols!");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("User`s age must be at least 18 years old!");
        }
        return storageDao.add(user);
    }

    private boolean isPasswordComplex(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialSymbol = false;

        for (char a : password.toCharArray()) {
            if (Character.isUpperCase(a)) {
                hasUpperCase = true;
            }

            if (Character.isLowerCase(a)) {
                hasLowerCase = true;
            }

            if (Character.isDigit(a)) {
                hasDigit = true;
            }

            if ((!Character.isLetterOrDigit(a))) {
                hasSpecialSymbol = true;
            }
        }

        return hasUpperCase && hasLowerCase & hasDigit & hasSpecialSymbol;
    }
}
