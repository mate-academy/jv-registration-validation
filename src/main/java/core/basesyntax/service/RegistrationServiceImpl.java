package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidAgeException;
import core.basesyntax.exceptions.InvalidLoginException;
import core.basesyntax.exceptions.InvalidPasswordException;
import core.basesyntax.exceptions.InvalidUserException;
import core.basesyntax.exceptions.LoginAlreadyExistsException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be 'null'");
        }

        if (Storage.people.contains(user)) {
            throw new LoginAlreadyExistsException("User with this login already exists "
                    + user.getLogin());
        }

        String login = user.getLogin();
        if (login == null) {
            throw new InvalidLoginException("Login cannot be null");
        }

        if (login.length() < 6) {
            throw new InvalidLoginException("Unacceptable login length: " + login.length());
        }

        String password = user.getPassword();
        if (password == null) {
            throw new InvalidPasswordException("Password cannot be null");
        }

        if (password.length() < 6) {
            throw new InvalidPasswordException("Unacceptable password length: "
                    + password.length());
        }

        Integer age = user.getAge();
        if (age == null) {
            throw new InvalidAgeException("Age cannot be null");
        }

        if (age < 18) {
            throw new InvalidAgeException("User is too young: " + age);
        }

        Storage.people.add(user);

        return user;
    }
}
