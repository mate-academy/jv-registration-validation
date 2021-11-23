package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int AGE = 18;
    private Storage storage = new Storage();
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("You havnt entered any age");
        }
        if (user.getPassword() == null || user.getPassword() == "") {
            throw new RuntimeException("Password can not be empty");
        }
        if (user.getLogin() == null || user.getLogin() == "") {
            throw new RuntimeException("Login can not be empty");
        }
        if (user == null) {
            throw new RuntimeException("Enter data");
        }
        for (int i = 0; i < Storage.people.size(); i++) {
            if (storageDao.get(user.getLogin()).equals(user.getLogin())) {
                throw new RuntimeException("User is allready exist !");
            }
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User is less then 18 !");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password contains less then 6 symbols !");
        }
        storageDao.add(user);
        return user;
    }
}
