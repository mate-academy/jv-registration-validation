package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private final StorageDaoImpl storageDao;

    @Override
    public User register(User user) {
        if (user == null || user.getLogin().length() <= 6 || storageDao.get(user.getLogin()).equals(user.getLogin())
                || user.getPassword().length() <=6 || user.getAge() <= 18) {
            throw new InvalidDataException("Data is invalid");
        }
        storageDao.add(user);
        return user;
    }

    public RegistrationServiceImpl(StorageDao dao) {
        this.storageDao = (StorageDaoImpl) dao;
    }
}
