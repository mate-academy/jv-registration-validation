package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USERS_AGE = 18;
    private static final int MAX_USERS_AGE = 115;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Please create your profile");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Please create your login");
        }
        if (user.getAge() > MAX_USERS_AGE) {
            throw new RuntimeException("Please use truly age");
        }
        if (user.getAge() < MIN_USERS_AGE) {
            throw new RuntimeException("Our users must be older than 18 years!");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password must be longer than 5 symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login already belongs to a registered user.");
        }
        return storageDao.add(user);
    }
}
