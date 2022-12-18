package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login can't be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationServiceException("Login must be more than"
                    + MIN_PASSWORD_LENGTH + "characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new RegistrationServiceException("Not valid age");
        }
        for (User userFromStorage : Storage.people) {
            if (userFromStorage.getLogin().equals(user.getLogin())) {
                throw new RegistrationServiceException("This login is not available");
            }
        }
        StorageDaoImpl storageDaoImp = new StorageDaoImpl();
        return storageDaoImp.add(user);
    }
}

