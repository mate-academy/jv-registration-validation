package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || password == null || age == null) {
            throw new InvalidUserException(buildErrorMessageForNullFields(login, password, age));
        }
        if (login.length() < 6) {
            throw new InvalidUserException("Login must be at least 6 characters long");
        }
        if (password.length() < 6) {
            throw new InvalidUserException("Password must be at least 6 characters long");
        }
        if (age < 18) {
            throw new InvalidUserException("User must be at least 18 years old");
        }
        User existingUser = storageDao.get(login);
        if (existingUser != null) {
            throw new InvalidUserException("User is already registered");
        }
    }

    private String buildErrorMessageForNullFields(String login, String password, Integer age) {
        StringBuilder builder = new StringBuilder();
        if (login == null) {
            builder.append("login ");
        }
        if (password == null) {
            builder.append("password ");
        }
        if (age == null) {
            builder.append("age");
        }
        return "Following field(s) can't be null: " + builder.toString();
    }
}
