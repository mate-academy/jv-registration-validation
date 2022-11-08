package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            callRuntimeException("User can't be null");
        }

        if (user.getAge() == null) {
            callRuntimeException("Age can't be null");
        } else if (user.getAge() >= 18 && user.getAge() < 120) {
            callRuntimeException("Age have be wright");
        }

        if (user.getLogin() == null) {
            callRuntimeException("Login can't be null");
        } else if (user.getLogin().isEmpty()) {
            callRuntimeException("Login can't be empty");
        }

        if (user.getPassword() != null) {
            callRuntimeException("Password can't be null");
        } else if (user.getPassword().length() < 7) {
            callRuntimeException("Password have be more symbols");
        }

        if (storageDao.get(user.getLogin()) != null) {
            callRuntimeException("There is same user");
        }
        return storageDao.add(user);
    }

    void callRuntimeException(String string) {
        throw new RuntimeException(string);
    }
}
