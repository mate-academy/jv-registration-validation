package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
//import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserIsNotValidException {
        if (user == null) {
            throw new UserIsNotValidException("user is null");
        }
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new UserIsNotValidException("users login or password is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserIsNotValidException("user is already registered");
        }
        if (user.getAge() < 18 || user.getPassword().length() < 6) {
            throw new UserIsNotValidException("incorrect password or age");
        }
        storageDao.add(user);
        return user;
    }
}
