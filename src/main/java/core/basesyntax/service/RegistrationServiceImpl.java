package core.basesyntax.service;

import core.basesyntax.RegistrationServiceException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_AND_EMAIL_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("User registration failed."
                    + " User object is null."
                    + " Please provide valid user information.");
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("User registration failed."
                    + " The login field is empty or null."
                    + " Please provide a valid login.");
        }
        if (user.getLogin().length() < MIN_PASSWORD_AND_EMAIL_LENGTH) {
            throw new RegistrationServiceException("User registration failed."
                    + " The login provided is too short."
                    + " Please use a login that contains at least "
                    + MIN_PASSWORD_AND_EMAIL_LENGTH + " characters.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User registration failed."
                    + " The login '"
                    + user.getLogin() + "' is already in use."
                    + " Please choose a different login.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("User registration failed."
                    + " The password field cannot be empty."
                    + " Please provide a valid password.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_AND_EMAIL_LENGTH) {
            throw new RegistrationServiceException("User registration failed."
                    + " The password provided does not meet the minimum length requirement."
                    + " Please use a password of at least 8 characters.");
        }
        if (user.getAge() == null) {
            throw new RegistrationServiceException("User registration failed."
                    + " The age field is missing or null."
                    + " Please provide a valid age to complete the registration.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationServiceException("User registration failed."
                    + " The minimum age requirement is "
                    + MIN_USER_AGE + " years. Please make sure you are at least "
                    + MIN_USER_AGE + " years old to register.");
        }
        return storageDao.add(user);
    }
}
