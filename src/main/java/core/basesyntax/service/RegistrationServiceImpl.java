package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Not valid age");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is not available");
        }
        return storageDao.add(user);
        }
    }
