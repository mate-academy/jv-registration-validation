package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static int MIN_AGE = 18;
    private static int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User equals null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Invalid age");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("The password is too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is already such login in the system");
        }
        if (!user.getLogin().contains("@")) {
            throw new RuntimeException("Login should contain @");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new RuntimeException("No numerals at the start of the email");
        }
        for (int i = 0; i < user.getLogin().length(); i++) {
            if (Character.isUpperCase(user.getLogin().charAt(i))) {
                throw new RuntimeException("No uppercase letters in email");
            }
        }
        storageDao.add(user);
        return user;
    }
}
