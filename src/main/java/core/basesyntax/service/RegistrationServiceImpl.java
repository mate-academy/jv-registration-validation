package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            checkFieldsForNull(user);
            boolean isAllFieldsCorrect = checkerOfFields(user);
            if (isAllFieldsCorrect) {
                storageDao.add(user);
                return user;
            }
        }
        return null;
    }

    private boolean checkerOfFields(User user) {
        if (user.getAge() >= 18
                && user.getPassword().length() >= 6
                && user.getId() > 0) {
            return true;
        }
        return false;
    }

    private void checkFieldsForNull(User user) {
        if (user.getLogin() == null
                || user.getAge() == null
                || user.getId() == null
                || user.getPassword() == null) {
            throw new RuntimeException("No one field can be null");
        }
    }
}
