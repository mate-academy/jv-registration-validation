package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int MINIMAL_AGE = 18;
    private final int MINIMAL_LENGTH = 6;

    @Override
    public User register(User user) throws ImpossibleRegisterUserException {
        if (user == null) {
            throw new ImpossibleRegisterUserException("The user is null");
        } else if (user.getLogin() == null || user.getLogin().length() < MINIMAL_LENGTH) {
            throw new ImpossibleRegisterUserException("This login is null or too short");
        } else if (user.getPassword() == null || user.getPassword().length() < MINIMAL_LENGTH) {
            throw new ImpossibleRegisterUserException("This password is null or too short");
        } else if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new ImpossibleRegisterUserException("Age is null or less than MINIMAL_AGE");
        } else {
            List<User> copy = new ArrayList<>(Storage.people);
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i).getLogin().equals(user.getLogin())) {
                    throw new ImpossibleRegisterUserException("A user with this login already exists.");
                }
            }
            storageDao.add(user);
        }
        return user;
    }
}
