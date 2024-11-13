package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (userValidation(user)) {
            return storageDao.add(user);
        }
        return null;
    }

    private boolean userValidation(User user) {
        return userNullCheck(user)
                && userValidateLoginExist(user.getLogin())
                && userValidateLoginLength(user.getLogin())
                && userValidatePasswordLength(user.getPassword())
                && userValidateAge(user.getAge());
    }

    private boolean userNullCheck(User user) {
        return user != null
                && user.getLogin() != null
                && user.getPassword() != null
                && user.getAge() != null;
    }

    private boolean userValidateLoginExist(String userLogin) {
        return storageDao.get(userLogin) == null;
    }

    private boolean userValidateLoginLength(String userLogin) {
        return userLogin.length() >= 6;
    }

    private boolean userValidatePasswordLength(String userPassword) {
        return userPassword.length() >= 6;
    }

    private boolean userValidateAge(int age) {
        return age >= 18;
    }

    public void removeByLogin(String login) {
        storageDao.removeByLogin(login);
    }
}
