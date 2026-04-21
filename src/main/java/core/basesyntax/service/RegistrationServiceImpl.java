package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private User newUser;

    @Override
    public User register(User user) throws CustomException {
        if (user == null) {
            throw new CustomException("User can not be null");
        }

        if (user.getAge() == null || user.getLogin() == null
                || user.getPassword() == null) {
            throw new CustomException("fields can not be null");
        }

        if (user.getAge() < 18
                || user.getAge() > 10
                || user.getPassword().length() < 6
                || user.getLogin().length() < 6) {
            throw new CustomException("invalid fields");
        }

        if (storageDao.get(user.getLogin()).equals(user)) {
            throw new CustomException("user already exist");
        } else if (storageDao.get(user.getLogin()) == null) {
            newUser = storageDao.add(user);
        }
        return newUser;
    }
}
