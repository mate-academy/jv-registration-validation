package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegisterDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_AGE = 18;
    private static final int MIN_CHAR_SUM = 6;
    private final StorageDao storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegisterDataException("login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegisterDataException("password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegisterDataException("age can't be null");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RegisterDataException("user with same login: " + user.getLogin()
                    + " already exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterDataException("user age: " + user.getAge() + " less than 18");
        }
        int charsSum;
        if ((charsSum = countChars(user.getLogin())) < MIN_CHAR_SUM) {
            throw new RegisterDataException("user login have: " + charsSum
                    + " chars that less than min required amount(6)");
        }
        if ((charsSum = countChars(user.getPassword())) < MIN_CHAR_SUM) {
            throw new RegisterDataException("user password have: " + charsSum
                    + " chars that less than min required amount(6)");
        }
        return storage.add(user);
    }

    private int countChars(String userData) {
        int count = 0;
        for (int i = 0; i < userData.length(); i++) {
            if (Character.isLetter(userData.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}
