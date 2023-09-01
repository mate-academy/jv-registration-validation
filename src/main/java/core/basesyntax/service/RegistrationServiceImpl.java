package core.basesyntax.service;

import core.basesyntax.DataNotVaidExeption;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGHT = 6;
    private static final int USER_PWD_LENGHT = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new DataNotVaidExeption("User can`t be NULL");
        }
        if (user.getPassword() == null || user.getLogin() == null) {
            throw new DataNotVaidExeption("Login and password can`t be null");
        }
        if (user.getAge() < 0) {
            throw new DataNotVaidExeption("Age not correct");
        }
        if (user.getLogin().length() < LOGIN_MIN_LENGHT) {
            throw new DataNotVaidExeption("Login must be longer than 6 characters");
        }
        if (user.getPassword().length() < USER_PWD_LENGHT) {
            throw new DataNotVaidExeption("Password must be longer than 6 characters");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new DataNotVaidExeption("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + USER_MIN_AGE);
        }
        if(user.getLogin() != null) {
            throw new DataNotVaidExeption("User not unique");
        }
        return storageDao.add(user);

    }
}
