package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private StorageDaoImpl storageDao;

    @Override
    public User register(User user) {
        if (user.getId() != null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("incorrectly entered data");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("you entered the wrong age");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("unreliable password");
        }
        for (User user1 : Storage.people) {
            if (user.getLogin().equals(user1.getLogin())) {
                throw new RuntimeException("such a user already exists");
            }
        }
        storageDao = new StorageDaoImpl();
        storageDao.add(user);
        return user;
    }
}
