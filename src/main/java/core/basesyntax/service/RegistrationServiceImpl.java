package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() >= 18 && user.getPassword().length() >= 6 && Storage.people.size() == 0) {
            return storageDao.add(user);
        }
        boolean isAdded = true;
        for (int i = 0; i < Storage.people.size(); i++) {
            if (user.getLogin().equals(Storage.people.get(i).getLogin())) {
                isAdded = false;
                break;
            }
        }
        if (isAdded == true) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Invalid data");
    }

    @Override
    public int getSize() {
        return Storage.people.size();
    }

    @Override
    public void reset() {
        Storage.people.clear();
    }
}
