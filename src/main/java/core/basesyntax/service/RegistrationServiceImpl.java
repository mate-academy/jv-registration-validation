package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int INITIAL_YEAR = 18;
    private static final int INITIAL_PASS_LENGTH = 6;
    private static final int INITIAL_LOGIN_LENGTH = 4;

    @Override
    public User register(User user) {
        StorageDaoImpl data = new StorageDaoImpl();
        if (user == null
                || user.getAge() == null
                || user.getPassword() == null
                || user.getLogin() == null) {
            throw new RuntimeException();
        }
        if (user.getAge() >= INITIAL_YEAR
                && user.getPassword().length() >= INITIAL_PASS_LENGTH
                && data.get(user.getLogin()) == null
                && user.getLogin().length() >= INITIAL_LOGIN_LENGTH) {
            data.add(user);
            return user;
        }
        throw new RuntimeException();
    }
}
