package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationServiceException("Invalid user :"
                    + " User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidRegistrationServiceException("Invalid login :"
                    + " Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidRegistrationServiceException("Invalid password :"
                    + " Password cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationServiceException("Invalid registration: "
                    + "The user is already registered");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new InvalidRegistrationServiceException("Invalid login : "
                    + "The login is too short");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidRegistrationServiceException("Invalid password: "
                    + "The password is too short");
        }
        if (user.getLogin().contains(" ")) {
            throw new InvalidRegistrationServiceException("Invalid login : "
                    + "Login can't contain spaces");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidRegistrationServiceException("Invalid user : "
                    + "User must be adult");
        }
        storageDao.add(user);
        return user;
    }
}
