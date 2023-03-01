package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_PASSWORD_LENGTH = 8;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        if (user.getLogin() == null) {
            throw new CustomException("Login can not be null");
        }
        if (user.getPassword() == null) {
            throw new CustomException("Password can not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("Login exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new CustomException("Age can not be less then 18");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new CustomException("Password can not be less then 8 characters");
        }
        storageDao.add(user);
        return user;
    }
}
