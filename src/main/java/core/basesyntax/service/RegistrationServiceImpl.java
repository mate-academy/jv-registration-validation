package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_COUNT_SYMBOL = 6;
    public static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new CustomException("The user is not registered in the network");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("User with this login already exists");
        }
        if (user.getLogin().length() < MIN_COUNT_SYMBOL) {
            throw new CustomException("Is not valid login");
        }
        if (user.getPassword().length() < MIN_COUNT_SYMBOL) {
            throw new CustomException("Is password not valid");
        }
        if (user.getAge() < VALID_AGE) {
            throw new CustomException("You are under 18 years old");
        }
        user = storageDao.add(user);
        return user;
    }
}
