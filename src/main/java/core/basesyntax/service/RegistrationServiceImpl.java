package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        try {
            if (user == null) {
                throw new UserRegistrationExceptions("User cannot be null");
            }
            if (user.getLogin() == null) {
                throw new UserRegistrationExceptions("Login is empty.");
            }
            if (user.getLogin().length() < 6) {
                throw new UserRegistrationExceptions(
                        "Login is too short, has to be at least 6 characters.");
            }
            if (user.getPassword() == null) {
                throw new UserRegistrationExceptions("Password is empty.");
            }
            if (user.getPassword().length() < 6) {
                throw new UserRegistrationExceptions(
                        "Password is too short, has to be at least 6 characters.");
            }
            if (user.getAge() == null) {
                throw new UserRegistrationExceptions("Age is empty.");
            }
            if (user.getAge() < 18) {
                throw new UserRegistrationExceptions("Age has to be at least 18 years.");
            }
            if (storageDao.get(user.getLogin()) != null) {
                throw new UserRegistrationExceptions("Login already exists.");
            }
            storageDao.add(user);
        } catch (UserRegistrationExceptions e) {
            throw new UserRegistrationExceptions(e.getMessage());
        }
        return user;
    }
}
