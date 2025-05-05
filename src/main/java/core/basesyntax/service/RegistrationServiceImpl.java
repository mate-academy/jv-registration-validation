package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGHT_PASS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login " + user.getLogin() + " has already exists");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("For registred your age should be" + MIN_AGE + " years old");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGHT_PASS) {
            throw new RuntimeException("Password can't be less" + MIN_LENGHT_PASS + " symbols");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Your age can't be null");
        }

        return storageDao.add(user);
    }
}
