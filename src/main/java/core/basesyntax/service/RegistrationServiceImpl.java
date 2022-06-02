package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_USER_AGE = 18;
    private Storage storage = new Storage();
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Enter data");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("You haven´t entered any age");
        }
        if (user.getPassword() == null || user.getPassword().length() == 0) {
            throw new RuntimeException("Password can not be empty");
        }
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new RuntimeException("Login can not be empty");
        }
        if ((storageDao.get(user.getLogin()) != null)) {
            throw new RuntimeException("User is allready exist !");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User is less then 18 !");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password contains less then 6 symbols !");
        }
        storageDao.add(user);
        return user;
    }
}
