package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    static private final int MIN_AGE = 18;
    static private final int MAX_AGE = 120;
    static private final int MIN_LETTERS = 6;


    @Override
    public User register(User user) {
        if (user == null) {
            throwRuntimeException("User can't be null");
        }
        if (user.getAge() == null) {
            throwRuntimeException("Age can't be null");
        } else if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throwRuntimeException("Age have be wright");
        }
        if (user.getLogin() == null) {
            throwRuntimeException("Login can't be null");
        } else if (user.getLogin().isEmpty()) {
            throwRuntimeException("Login can't be empty");
        }
        if (user.getPassword() == null) {
            throwRuntimeException("Password can't be null");
        } else if (user.getPassword().length() <= MIN_LETTERS) {
            throwRuntimeException("Password have be more symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throwRuntimeException("There is same user");
        }
        return storageDao.add(user);
    }

    private void throwRuntimeException(String string) {
        throw new RuntimeException(string);
    }
}
