package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegisterDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new InvalidRegisterDataException("Not all data is entered");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegisterDataException("User with your login already exist");
        } else if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidRegisterDataException("Your login must have 6 or more characters.");
        } else if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidRegisterDataException("Your password must have 6 or more characters.");
        } else if (user.getAge() < MIN_AGE) {
            throw new InvalidRegisterDataException("You are under 18 years old.");
        }
        return storageDao.add(user);
    }
}
