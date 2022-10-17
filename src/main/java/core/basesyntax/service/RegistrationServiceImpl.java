package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User equals null");
        }
        final String login = user.getLogin();
        final String password = user.getPassword();
        final String usersId = String.valueOf(user.getId());
        if (user.getAge() < 18) {
            throw new RuntimeException("Invalid age");
        }
        if (password.length() < 6) {
            throw new RuntimeException("The password is too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is already such login in the system");
        }
        if (!login.contains("@")) {
            throw new RuntimeException("Login should contain @");
        }
        if (!Character.isLetter(login.charAt(0))) {
            throw new RuntimeException("No numerals at the start of the email");
        }
        if (usersId.length() != 10) {
            throw new RuntimeException("usersId should consist of 10 digits");
        }
        for (int i = 0; i < login.length(); i++) {
            if (Character.isUpperCase(login.charAt(i))) {
                throw new RuntimeException("No uppercase letters in email");
            }
        }
        storageDao.add(user);
        return user;
    }
}
