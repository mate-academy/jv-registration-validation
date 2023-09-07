package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationExseption("This login already exist");
        } else if (user.getLogin().length() < 6) {
            throw new RegistrationExseption("Too short login");
        } else if (user.getAge() < 18) {
            throw new RegistrationExseption("Too small age");
        } else if (user.getPassword().length() < 6) {
            throw new RegistrationExseption("Too short password");
        } else {
            storageDao.add(user);
        }
        return user;
    }
}
