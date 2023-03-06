package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;
    private static final int MAX_AGE = 122;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserExist(user);
        checkPassword(user);
        checkAge(user);
        checkId(user);
        checkLogin(user);

        return storageDao.add(user);
    }

    private void checkUserExist(User user) {
        if (user == null) {
            throw new RegistrationException("User not found");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new RegistrationException("Password less than 6 characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge());
        }
    }

    private void checkId(User user) {
        if (user.getId() != null) {
            throw new RegistrationException("User should not have id assigned yet");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new RegistrationException("Can't find login");
        }
        if (storageDao.get(user.getLogin()) != null
                && user.getLogin().equals(storageDao.get(user.getLogin()).getLogin())) {
            throw new RegistrationException("This user already exist");
        }
    }
}
