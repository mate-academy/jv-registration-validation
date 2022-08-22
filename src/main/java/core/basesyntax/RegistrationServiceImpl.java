package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_MIN = 18;
    private static final int AGE_MAX = 99;
    private static final int PASS_LENGTH_LIMIT = 6;
    private static final int LOGIN_LENGTH_LIMIT = 20;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Can't be NULL");
        }
        if (user.getAge() < AGE_MIN) {
            throw new RuntimeException("User is younger than 18 years");
        }
        if (user.getAge() > AGE_MAX) {
            throw new RuntimeException("User is older than 99 years");
        }
        if (ifLoginTheSame(user.getLogin())) {
            return null;
        }
        if (user.getLogin().length() > LOGIN_LENGTH_LIMIT) {
            throw new RuntimeException("Login more than 20 characters");
        }
        if (user.getPassword().length() < PASS_LENGTH_LIMIT) {
            throw new RuntimeException("Password less than 6 characters, it should be 6 or more");
        }
        return storageDao.add(user);
    }

    private boolean ifLoginTheSame(String login) {
        for (int i = 0; i < Storage.people.size(); i++) {
            if (login.equals(Storage.people.get(i).getLogin())) {
                return true;
            }
        }
        return false;
    }
}
