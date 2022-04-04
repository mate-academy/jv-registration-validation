package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final StorageDaoImpl STORAGE_DAO = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || (user.getAge() == null || user.getPassword() == null
                || user.getLogin() == null || user.getId() == null)) {
            throw new RuntimeException("Invalid user");
        }
        if (STORAGE_DAO.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User should be 18 y. o. to register");
        }
        if (user.getLogin().length() < 6) {
            throw new RuntimeException("User login is to short. Needs at least 6 characters");
        }

        return STORAGE_DAO.add(user);
    }
}
