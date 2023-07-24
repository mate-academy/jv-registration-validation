package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        //Initial User validation
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        if (Storage.people.contains(user)) {
            throw new RegistrationException("This user is already existed in the Storage!");
        }
        //User's login validation
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login can't be null!");
        }
        if (user.getLogin().isBlank()) {
            throw new RegistrationException("User's login can't consist only white spaces!");
        }
        if (user.getLogin().length() < MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("User's login should be longer than "
                    + MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS + "!");
        }
        //User's password validation
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password can't be null!");
        }
        if (user.getPassword().isBlank()) {
            throw new RegistrationException("User's password can't consist only white spaces");
        }
        if (user.getPassword().length() < MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS) {
            throw new RegistrationException("User's password should be longer than "
                    + MIN_LOGIN_PASSWORD_NUMBER_OF_CHARACTERS + "!");
        }
        //User's age validation
        if (user.getAge() == null) {
            throw new RegistrationException("User's age can't be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User can't be registered with less age than min: "
                    + MIN_AGE + "!");
        }
        return storageDao.add(user);
    }
}
