package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        if (user.getAge() == null || user.getPassword() == null || user.getLogin() == null) {
            throw new CustomException("User login,password or age is null!" + user.getAge() + user.getPassword() + user.getLogin());
        }
        if (user.getLogin().isEmpty() || (user.getPassword().isEmpty())) {
            throw new CustomException("User login or password is empty!" + user.getLogin() + user.getPassword());
        }
        if ((user.getLogin().length()) < 6) {
            throw new CustomException("User login must be at least 6 characters long! - "
                    + user.getLogin());
        }
        if (user.getAge() < 18) {
            throw new CustomException("The user must not be less than 18 years old! " + user.getAge());
        }
        if ((user.getPassword().length() < 6)) {
            throw new CustomException("User password is need to be at least 6 letters! - "
                    + user.getPassword());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("User with this login is already register! - "
                    + user.getLogin());
        }
        return storageDao.add(user);
    }
}
