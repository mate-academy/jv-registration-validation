package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null
                && user.getLogin() != null
                && user.getLogin().length() > 0
                && user.getLogin().equals(user.getLogin().replaceAll("[^\\da-zA-Z 0-9]", ""))
                && !user.getLogin().equals(user.getPassword())
                && user.getPassword() != null
                && user.getAge() >= MIN_AGE
                && user.getPassword().length() >= MIN_PASS_LENGTH) {
            return storageDao.add(user);
        }
        throw new RuntimeException("There is been user with such login in the Storage yet\n"
               + "OR the user is not at least 18 years old\n"
               + "OR user password is not at least 6 characters"
               + "OR user login contains bad characters"
               + "OR user login is not at least 1 character");
    }
}
