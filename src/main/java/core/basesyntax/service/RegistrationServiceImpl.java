package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null) {
            if (user.getLogin() != null) {
                String pureLogin = user.getLogin().trim();
                user.setLogin(pureLogin);
                if (!user.getLogin().equals("") && storageDao.get(user.getLogin()) == null) {
                    if (user.getAge() != null && user.getAge() >= 18) {
                        if (user.getPassword() != null) {
                            String purePassword = user.getPassword().trim();
                            user.setPassword(purePassword);
                            if (user.getPassword().length() >= 6) {
                                storageDao.add(user);
                                return user;
                            }
                        }
                        throw new RuntimeException("The password should be at least 6 symbols");
                    }
                    throw new RuntimeException("The age should be at least 18 years old");
                }
            }
            throw new RuntimeException("Invalid login or already exists");
        }
        throw new RuntimeException("Invalid input user");
    }
}

