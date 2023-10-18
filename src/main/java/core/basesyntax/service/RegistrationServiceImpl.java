package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationException("User cannot be 'null'");
        }

        if (Storage.people.contains(user)) {
            throw new InvalidRegistrationException("User with this login already exists "
                    + user.getLogin());
        }

        String login = user.getLogin();
        if (login == null) {
            throw new InvalidRegistrationException("Login cannot be null");
        }

        if (login.length() < 6) {
            throw new InvalidRegistrationException("Unacceptable login length: " + login.length());
        }

        String password = user.getPassword();
        if (password == null) {
            throw new InvalidRegistrationException("Password cannot be null");
        }

        if (password.length() < 6) {
            throw new InvalidRegistrationException("Unacceptable password length: "
                    + password.length());
        }

        Integer age = user.getAge();
        if (age == null) {
            throw new InvalidRegistrationException("Age cannot be null");
        }

        if (age < 18) {
            throw new InvalidRegistrationException("User is too young: " + age);
        }

        Storage.people.add(user);

        return user;
    }
}
