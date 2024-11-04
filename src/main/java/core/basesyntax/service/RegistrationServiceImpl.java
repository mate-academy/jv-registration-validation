package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    public static final int MIN_LOGPASS = 6;

    @Override
    public User register(User user) throws CustomeException {
        if (user.getAge() == null || user.getPassword() == null || user.getLogin() ==  null) {
            throw new CustomeException("User login,password or age is null!");
        }
        if (user.getLogin().isEmpty() || (user.getPassword().isEmpty())) {
            throw new CustomeException("User login or password is empty!");
        }
        if ((user.getLogin().length()) < MIN_LOGPASS) {
            throw new CustomeException("User login is need to be at least 6 letters! - " + user.getLogin());
        }
        if (user.getAge() < 18) {
            throw new CustomeException("User age is under 18! " + user.getAge());
        }
        if ((user.getPassword().length() < MIN_LOGPASS)) {
            throw new CustomeException("User password is need to be at least 6 letters! - " + user.getPassword());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomeException("User with this login is already register! - " + user.getLogin());
        }
        return storageDao.add(user);
    }
}
