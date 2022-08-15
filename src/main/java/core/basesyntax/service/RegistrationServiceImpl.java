package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.UsersAgeNotValidException;
import core.basesyntax.exceptions.UsersPasswordNotValidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new NullPointerException("You must fill all the fields!");
        }
        if (storageDao.get(user.getLogin()) == null) {
            if (user.getAge() >= 18) {
                if (user.getPassword().length() >= 6) {
                    storageDao.add(user);
                    return user;
                } else {
                    throw new UsersPasswordNotValidException("Password must be at least 6 symbols");
                }
            } else {
                throw new UsersAgeNotValidException("You should be older!");
            }
        } else {
            throw new UserAlreadyExistException("This login already exist!");
        }
    }
}
