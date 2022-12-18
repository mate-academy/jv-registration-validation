package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;

    @Override
    public User register(User user) throws CustomException {
        if (user.getLogin() == null) {
            throw new CustomException("empty login");
        }
        if (user.getPassword() == null) {
            throw new CustomException("empty password");
        }
        if (user.getAge() < MIN_AGE) {
            throw new CustomException("the age is under 18");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new CustomException("password length must be more than 6");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("that user already exist in storage");
        }
        return storageDao.add(user);
    }
}
