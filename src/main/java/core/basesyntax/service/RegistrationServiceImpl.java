package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.NotValidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        try {
            if (user == null) {
                throw new NotValidDataException("null user.");
            }
            if (tooYongUser(user)) {
                throw new NotValidDataException("too young user.");
            }
            if (tooShortLogin(user)) {
                throw new NotValidDataException("too short user's login.");
            }
            if (tooShortPassword(user)) {
                throw new NotValidDataException("too short user's password.");
            }
            if (existedLogin(user)) {
                throw new NotValidDataException("This login is existed.");
            }
        } catch (NotValidDataException e) {
            throw new RuntimeException(e);
        }
        storageDao.add(user);
        return user;
    }

    private boolean existedLogin(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private boolean tooShortPassword(User user) {
        final int passwordLengthAtLeastRequire = 6;
        return user.getPassword().length() < passwordLengthAtLeastRequire;
    }

    private boolean tooShortLogin(User user) {
        final int lengthAtLeastRequire = 6;
        return user.getLogin().length() < lengthAtLeastRequire;
    }

    private boolean tooYongUser(User user) {
        final int ageAtLeastRequire = 18;
        return user.getAge() < ageAtLeastRequire;
    }
}