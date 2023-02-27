package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int MAX_VALID_AGE = 115;
    private static final int VALID_COUNT_OF_CHARACTER = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        if (user == null) {
            throw new CustomException("Expected " + CustomException.class.getName()
                    + " to be thrown for the null user, but it wasn't");
        }
        if (user.getAge() < VALID_AGE || user.getAge() > MAX_VALID_AGE || user.getAge() == null) {
            throw new CustomException("Age should be above" + VALID_AGE
                    + " or equal and less than" + MAX_VALID_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("User with the same Login already exist!");
        }
        for (char chars : user.getPassword().toCharArray()) {
            if (!Character.isLetter(chars) || !Character.isDigit(chars)
                    || user.getPassword().length() < VALID_COUNT_OF_CHARACTER
                    || user.getPassword() == null) {
                throw new CustomException("Your Password should include "
                        + VALID_COUNT_OF_CHARACTER
                        + "and more characters! Your Password cannot contains any special symbols");
            }
        }
        return storageDao.add(user);
    }
}
