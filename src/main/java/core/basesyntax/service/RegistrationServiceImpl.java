package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_COUNT_CHARACTER = 6;
    static final String EMPTY_LINE = "";

    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() < MIN_AGE || user.getAge() == null
                || user.getPassword() == null || user.getPassword().length() < MIN_COUNT_CHARACTER
                || user.getPassword().equals(EMPTY_LINE) || user.getLogin().equals(EMPTY_LINE)
                || user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Date is wrong!");
        }
        storageDao.add(user);
        return user;
    }
}
