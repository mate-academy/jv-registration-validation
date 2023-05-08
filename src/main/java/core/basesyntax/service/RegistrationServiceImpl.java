package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int CORRECT_AGE = 18;
    private static final int CHARACTER_AMOUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User shouldn't be null");
        }
        if (user.getAge() == null || user.getAge() < CORRECT_AGE) {
            throw new InvalidDataException("User's age must be over " + CORRECT_AGE);
        }
        if (user.getLogin() == null || user.getLogin().length() < CHARACTER_AMOUNT) {
            throw new InvalidDataException("Login must contain at least "
                    + CHARACTER_AMOUNT + " character");
        }
        if (user.getPassword() == null || user.getPassword().length() < CHARACTER_AMOUNT) {
            throw new InvalidDataException("Password must contain at least "
                    + CHARACTER_AMOUNT + " character");
        }
        if (user.getId() == null) {
            throw new InvalidDataException("Id can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Login already taken");
        }
        return user;
    }
}
