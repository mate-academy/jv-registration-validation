package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int LOWEST_AGE_THRESHOLD = 18;
    public static final int HIGHEST_AGE_THRESHOLD = 125;
    public static final int SHORTEST_PASSWORD_LENGTH = 6;
    private StorageDao storageDao;

    @Override
    public User register(User user) {
        storageDao = new StorageDaoImpl();
        isNull(user.getAge(), "Enter your age");
        isNull(user.getPassword(), "Enter your password, please!");
        isNull(user.getLogin(), "Enter your login");
        isAgeValid(user.getAge());
        isPasswordValid(user, user.getPassword());
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new RuntimeException("User with such login already exist");
    }

    public <T> void isNull(T object, String massage) {
        if (object == null) {
            throw new RuntimeException(massage);
        }
    }

    public void isAgeValid(Integer age) {
        if (age < LOWEST_AGE_THRESHOLD || age > HIGHEST_AGE_THRESHOLD) {
            throw new RuntimeException("Sorry, we cant register you."
                    + " Our users can't be younger then 18 and older then 125 years old");
        }
    }

    public void isPasswordValid(User user, String password) {
        if (user.getPassword().length() < SHORTEST_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password is too short.");
        }
    }
}
