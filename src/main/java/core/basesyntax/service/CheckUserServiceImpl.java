package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class CheckUserServiceImpl implements CheckUserService{
    private static final int LIMIT_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public Boolean checkNullRegister(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException(" \n Your date is Invalid "
                    + "(User, User's age, User's login, User's password: can not be null (empty)");
        }
        return false;
    }

    @Override
    public Boolean checkUserAge(User user) {
        if (user.getAge() < LIMIT_AGE) {
            throw new RuntimeException("\n Your date is Invalid (Your age is less) " + LIMIT_AGE);
        }
        return false;
    }

    @Override
    public Boolean checkSameUserLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("\n Your date is Invalid (Login is already registred)");
        }
        return false;
    }

    @Override
    public Boolean checkUserPassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("\n Your date is Invalid (Your password's length less) "
                    + MIN_PASSWORD_LENGTH);
        }
        return false;
    }
}
