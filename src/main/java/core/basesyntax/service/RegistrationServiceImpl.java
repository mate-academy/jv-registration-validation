package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int AGE_MIN = 18;
    private static final int AGE_MAX = 122;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationServiceException {
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Error, login is " + user.getLogin());
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationServiceException("Error, login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Error, password is " + user.getPassword());
        }
        if (user.getPassword().equals("")) {
            throw new RegistrationServiceException("Error, password cant be empty");
        }
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Error, age is " + user.getAge());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User with this login is already exists");
        }
        if (user.getAge() < AGE_MIN) {
            throw new RegistrationServiceException("User's age "
                    + user.getAge() + " is less than 18 years");
        }
        if (user.getAge() > AGE_MAX) {
            throw new RegistrationServiceException("User's age "
                    + user.getAge() + " is more than" + AGE_MAX + "years");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationServiceException("User's password is less than 6 characters");
        }
        return storageDao.add(user);
    }
}
