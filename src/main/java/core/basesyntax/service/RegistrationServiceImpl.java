package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.unchekedException.IncorrectInputDataException;

public class RegistrationServiceImpl implements RegistrationService {

    private final static int MIN_LENGTH = 6;
    private final static int MIN_ACQUIRED_AGE = 18;

    private boolean checkDoubleLogin(User user) {
        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLoginLength(User user) throws IncorrectInputDataException {
        if (user.getLogin() == null) {
            throw new IncorrectInputDataException("User login cannot be null");
        }
        if (user.getLogin().length() >= MIN_LENGTH) {
            return true;
        }
        return false;
    }

    private boolean checkPassLength(User user) throws IncorrectInputDataException {
        if (user.getPassword() == null) {
            throw new IncorrectInputDataException("Password cant be null");
        }
        if (user.getPassword().length() >= MIN_LENGTH) {
            return true;
        }
        return false;
    }

    @Override
    public User register(User user) throws IncorrectInputDataException {
        if (user.getAge() < 0 || user.getAge() < MIN_ACQUIRED_AGE) {
            throw new IncorrectInputDataException("User age must be positive and more than 18 years old");
        }
        if (checkDoubleLogin(user)
                && checkLoginLength(user)
                && checkPassLength(user)
                && user.getAge() >= MIN_ACQUIRED_AGE) {
            Storage.people.add(user);
        }
        return user;
    }
}
