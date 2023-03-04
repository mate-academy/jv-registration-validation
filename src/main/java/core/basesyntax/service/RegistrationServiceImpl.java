package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataForRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null) {
            throw new InvalidDataForRegistrationException("Can't register "
                    + "user with null age/login/password");
        }
        if (user.getAge() < 18 || user.getAge() > 130) {
            throw new InvalidDataForRegistrationException("Can't register "
                    + "user with age less than 18 and greater than 130");
        }
        if (user.getLogin().equals("")) {
            throw new InvalidDataForRegistrationException("Can't register "
                    + "user with empty login");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataForRegistrationException("Your password "
                    + "must contain at least six characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataForRegistrationException("User with "
                    + "this login already exists");
        }
        storageDao.add(user);
        return user;
    }
}
