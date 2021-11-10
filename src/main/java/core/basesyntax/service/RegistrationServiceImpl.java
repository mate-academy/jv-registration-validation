package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Incorrect entry about user");
        }
        if (user.getPassword() == null || user.getLogin() == null) {
            throw new RuntimeException("User's password or login is empty");
        }
        if ( user.getAge() < 18) {
            throw new RuntimeException(" User is not adult! Registration canceled.");
        }
        if ( user.getPassword().length() < 6) {
            throw new RuntimeException("User's password is very short. Registration canceled");
        }
        StorageDaoImpl dao = new StorageDaoImpl();
        if (dao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login is exist");
        }
        dao.add(user);
        return user;
    }
}
