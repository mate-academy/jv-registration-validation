package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user == null) { //null check
            throw new ValidationException("Can't add User. User is null.");
        }
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new ValidationException("Can't add User. User login is null or empty.");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("Can't add User. User password is null.");
        }
        if (user.getAge() == 0 || user.getAge() > 110) {
            throw new ValidationException("Can't add User. Wrong User age.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("Can't add User. User already exist.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("Can't add User. User age less then " + MIN_AGE + ".");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new ValidationException("Can't add User. User age less then " + MIN_AGE + ".");
        }
        storageDao.add(user);
        return user;
    }
}
