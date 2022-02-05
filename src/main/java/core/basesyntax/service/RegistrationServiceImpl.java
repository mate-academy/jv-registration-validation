package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LETTERS_IN_PASSWORD = 7;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("The age should be 18 or more and less then 100");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LETTERS_IN_PASSWORD) {
            throw new RuntimeException("The password should contain at least 6 characters"
                    + " and don't contain null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("The login should consist of letters and numbers only");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The same login has already existed");
        }
        return new StorageDaoImpl().add(user);
    }
}
