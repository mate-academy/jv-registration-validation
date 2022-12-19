package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl storageDaoImp = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Login must be more than"
                    + MIN_PASSWORD_LENGTH + "characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("Not valid age");
        }
        if (storageDaoImp.get(user.getLogin()) != null
                && storageDaoImp.get(user.getLogin()).getLogin().equals(user.getLogin())) {
            throw new RegistrationException("This login is not available");
        }
        return storageDaoImp.add(user);
    }
}

