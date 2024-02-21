package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int userPasswordLength = 6;
    private final int userAge = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            try {
                throw new UserAlreadyExistsException(user.getLogin());
            } catch (UserAlreadyExistsException e) {
                System.out.println(e.toString());
            }
        }
        if (user.getPassword().length() < userPasswordLength) {
            try {
                throw new PasswordLengthException(6);
            } catch (PasswordLengthException e) {
                System.out.println(e.toString());
            }
        }
        if (user.getAge() < userAge) {
            throw new IllegalArgumentException("The Age of user is not valid");
        }
        return user;
    }
}
