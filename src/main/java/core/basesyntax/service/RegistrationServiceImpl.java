package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int MAX_VALID_AGE = 115;
    private static final int VALID_COUNT_OF_CHARACTER = 6;
    private static final int MIN_COUNT_OF_LOGIN_CHARACTER = 1;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        if (user == null) {
            throw new CustomException("Expected " + CustomException.class.getName()
                    + " to be thrown for the null user. Null user is not allowed");
        }
        if (user.getPassword() == null) {
            throw new CustomException("Your Password should include cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_COUNT_OF_LOGIN_CHARACTER
                || user.getLogin().contains(" ")) {
            throw new CustomException("Your Login cannot contains whitespace. "
                    + "Your Login cannot empty!");
        }
        if (user.getAge() == null || user.getAge() < VALID_AGE || user.getAge() > MAX_VALID_AGE) {
            throw new CustomException("Age should be above" + VALID_AGE
                    + " or equal and less than" + MAX_VALID_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("User with the same Login already exist!");
        }
        for (char chars : user.getPassword().toCharArray()) {
            if (user.getPassword().length() < VALID_COUNT_OF_CHARACTER
                    || !Character.isLetter(chars) && !Character.isDigit(chars)) {
                throw new CustomException("Your Password should include "
                        + VALID_COUNT_OF_CHARACTER
                        + "and more characters! Your Password cannot contains any special symbols");
            }
        }
        return storageDao.add(user);
    }
}
