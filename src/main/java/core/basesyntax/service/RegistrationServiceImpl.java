package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        if (user == null) {
            throw new CustomException("User is null.");
        } else if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new CustomException("The parameters of user are null.");
        }
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null && user.getLogin().equals(storageDao.get(user.getLogin())
                .getLogin())) {
            throw new CustomException("There is an user with the same login.");
        } else if (user.getLogin().length() >= 6
                && user.getPassword().length() >= 6
                && user.getAge() >= 18) {
            storageDao.add(user);
            return user;
        } else {
            throw new CustomException("User doesn't have a valid data.");
        }
    }
}
