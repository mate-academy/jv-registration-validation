package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptionforservice.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MINIMUM_CHARACTERS_IN_STRING = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (countCaracterInPasswordAndLogin(user.getLogin())
                && countCaracterInPasswordAndLogin(user.getPassword())
                && MIN_AGE <= user.getAge()) {
            storageDao.add(user);
            return user;
        } else {
            throw new RegistrationException("Invalid data. Try again!");
        }
    }

    public boolean countCaracterInPasswordAndLogin(String data) throws RegistrationException {
        if (data == null) {
            throw new RegistrationException("Null in input");
        }
        int countLetter = 0;
        if (data.length() < MINIMUM_CHARACTERS_IN_STRING) {
            throw new RegistrationException("Yours data isn't valid. Try again.");
        }
        for (char letters : data.toLowerCase().toCharArray()) {
            if (letters >= 'a' && letters <= 'z') {
                countLetter++;
            }
        }
        return countLetter >= MINIMUM_CHARACTERS_IN_STRING;
    }
}
