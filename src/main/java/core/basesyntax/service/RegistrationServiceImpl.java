package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int INITIAL_YEAR = 18;
    private static final int INITIAL_PASS_LENGTH = 6;

    @Override
    public User register(User user) {
        StorageDaoImpl data = new StorageDaoImpl();
        if (user == null
                || user.getAge() == null
                || user.getPassword() == null
                || user.getLogin() == null) {
            throw new RuntimeException();
        }
        if (user != null
                && user.getAge() >= INITIAL_YEAR
                && user.getPassword().length() >= INITIAL_PASS_LENGTH
                && user.getLogin() != null) {
            data.add(user);
            return user;
        }
        throw new RuntimeException();
    }
}
