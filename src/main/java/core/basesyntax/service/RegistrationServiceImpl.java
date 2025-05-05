package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationServiceException {
        if (user == null) {
            throw new RegistrationServiceException("Empty user not allowed");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationServiceException("Empty login not allowed");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationServiceException("Empty password not allowed");
        }
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Empty age not allowed");
        }
        if (user.getAge() <= 0) {
            throw new RegistrationServiceException("User's age should be positive number");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationServiceException("User's login is too short");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationServiceException("User's password is too short");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationServiceException("User is too young");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User with such login already exist");
        }
        return storageDao.add(user);
    }
}
