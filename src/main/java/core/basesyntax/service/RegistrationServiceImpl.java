package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULT_AGE = 18;
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int POSITION_FIRST_CHARACTER = 0;
    private static final int MIN_LENGTH_OF_LOGIN = 3;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user.getPassword() == null || user.getAge() == null
                || user.getLogin() == null) {
            throw new NullPointerException("One or more fields of user equals null");
        }
        if (!Character.isLetter(user.getLogin().charAt(POSITION_FIRST_CHARACTER))) {
            throw new RuntimeException("Your login must start from a letter");
        }
        if (user.getLogin().length() < MIN_LENGTH_OF_LOGIN) {
            throw new RuntimeException("Your login length must be more than two letters");
        }
        if (storageDao.get(user.getLogin()) == null
                && user.getAge() >= ADULT_AGE
                && user.getPassword().length() >= MIN_LENGTH_OF_PASSWORD) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Your registration is failed, try one more time");
    }
}
