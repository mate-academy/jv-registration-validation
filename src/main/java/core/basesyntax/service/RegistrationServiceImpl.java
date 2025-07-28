package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    public User register(User user) {
            if (user == null) {
                throw new RegistrationException("User cannot be null");
            }
            String login = user.getLogin();
            String password = user.getPassword();
            Integer age = user.getAge();

            if (login == null) {
                throw new RegistrationException("Login cannot be empty");
            }
            if (login.length() < 6) {
                throw new RegistrationException("Login must be at least 6 character");
            }
            if (storageDao.get(login) != null) {
                throw new RegistrationException("This login is already used");
            }
            if(password == null) {
                throw new RegistrationException("Password cannot be empty");
            }
            if (password.length() < 6) {
                throw new RegistrationException("Your password must be at least 6 character");
            }
            if (age == null) {
                throw new RegistrationException("Age cannot be empty");
            }
            if (age < 18) {
                throw new RegistrationException("You are to young");
            }
            return storageDao.add(user);
        }
}
