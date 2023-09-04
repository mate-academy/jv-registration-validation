package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists!");
        }
        return storageDao.add(user);
    }

    private void checkPassword(String password){
        if (password == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (password.length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters long!");
        }
    }

    private void checkLogin(String login){
        if (login == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (login.length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters long!");
        }
    }

    private void checkAge(Integer age){
        if (age == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (age < 18) {
            throw new RegistrationException("User must be at least 18 years old!");
        }
    }
}
