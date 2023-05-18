package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LOGIN_LENGTH = 6;

    @Override
    public User register(User user) {

        isValid(user);
        return storageDao.add(user);

    }

    private void isValid(User user) throws UserDataException {
        if (isNull(user)) {
            throw new UserDataException("User is null.");
        }
        if (isNull(user.getId())) {
            throw new UserDataException("ID can't be null.");
        }
        if (isNull(user.getAge()) || user.getAge() < MIN_AGE) {
            throw new UserDataException("User's age must be over " + MIN_AGE);
        }
        if (isNull(user.getAge()) || user.getAge() > MAX_AGE) {
            throw new UserDataException("User's age is not valid.");
        }
        if (isNull(user.getLogin()) || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserDataException("User's login must at least " + MIN_LOGIN_LENGTH);
        }
        if (isNull(user.getPassword()) || user.getPassword().length() < MIN_LOGIN_LENGTH) {
            throw new UserDataException("User's password must at least " + MIN_LOGIN_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserDataException("Login already exist.");
        }
    }

    private static boolean isNull(Object object) {
        return object == null;
    }

}
