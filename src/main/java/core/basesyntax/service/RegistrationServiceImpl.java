package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 140;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid User");
        }
        StorageDao storageDao = new StorageDaoImpl();
        String userLogin = user.getLogin();
        String userPass = user.getPassword();
        int userAge = user.getAge();

        if (userLogin == null || userLogin.isEmpty() || !checkLogin(userLogin)
                || userPass == null || userPass.isEmpty()
                || userPass.length() < MIN_PASSWORD_LENGTH
                || userAge < MIN_AGE || userAge > MAX_AGE) {
            throw new RuntimeException("Invalid Data");
        }
        storageDao.add(user);

        return user;
    }

    private boolean checkLogin(String login) {
        return new StorageDaoImpl().get(login) == null;
    }
}
