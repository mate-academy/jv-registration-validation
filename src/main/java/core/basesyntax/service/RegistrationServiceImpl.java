package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE_USER = 18;

    @Override
    public User register(User user) {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        String login = user.getLogin();
        String password = user.getPassword();
        int age = user.getAge();
        if (age >= MIN_AGE_USER
                && password.length() >= MIN_LENGTH_PASSWORD
                && storageDao.get(login) == null
                && !login.equals("")
                && login.matches("^[a-zA-Z0-9]+$")
                && age <= 110
                && password.matches("^[a-zA-Z0-9]+$")) {
            storageDao.add(user);
        } else {
            throw new RuntimeException("Incorrect data!");
        }
        return user;
    }
}
