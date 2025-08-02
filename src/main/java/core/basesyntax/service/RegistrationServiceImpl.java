package core.basesyntax.service;

import core.basesyntax.UserNotRegisterException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new UserNotRegisterException("User is null.");
        }

        if (user.getLogin() == null) {
            throw new UserNotRegisterException("Login user's is null.");
        }

        if (user.getLogin().length() == 0) {
            throw new UserNotRegisterException("Login user's is empty string.");
        }
        if (user.getLogin().length() < 6) {
            throw new UserNotRegisterException("Login user`s must be at least 6.");
        }

        User userLogin = storageDao.get(user.getLogin());
        if (userLogin != null) {
            throw new UserNotRegisterException("User already exists.");
        }

        if (user.getPassword() == null) {
            throw new UserNotRegisterException("Password user's is null.");
        }

        if (user.getPassword().length() == 0) {
            throw new UserNotRegisterException("Password user's is empty string.");
        }

        if (user.getPassword().length() < 6) {
            throw new UserNotRegisterException("Password user`s must be at least 6.");
        }

        if (user.getAge() == null) {
            throw new UserNotRegisterException("Age user`s is null.");
        }

        if (user.getAge() == 0) {
            throw new UserNotRegisterException("Age user`s is 0.");
        }

        if (user.getAge() < 18) {
            throw new UserNotRegisterException("Age user`s is less 18.");
        }

        storageDao.add(user);

        return user;
    }
}
