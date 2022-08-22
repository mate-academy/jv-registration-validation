package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserLogin(user.getLogin());
        validateUserAge(user.getAge());
        return user;
    }

    private void validateUserAge(Integer age) {
        if (age == null) {
            throw new NullPointerException("User age can not be Null!");
        } else if (age < 18) {
            throw new RuntimeException("User is under 18 years old");
        }
    }

    private void validateUserLogin(String login) {
        if (login == null) {
            throw new NullPointerException("User login can not be Null!");
        } else if (login.isEmpty()) {
            throw new RuntimeException("User login can not be empty!");
        } else if (login.contains(" ")) {
            throw new RuntimeException("User login can not contain white spaces!");
        } else if (storageDao.get(login) != null) {
            throw new RuntimeException("Such user has already exist!!!");
        }
    }
}
