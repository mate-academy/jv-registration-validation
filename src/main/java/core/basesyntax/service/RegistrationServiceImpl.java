package core.basesyntax.service;

import core.basesyntax.DataNotVaidExeption;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
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
        if (Storage.people.size() > 0) {
            for (int i = 0; i < Storage.people.size(); i++) {

                if (storageDao.get(user.getLogin()).equals(user)) {
                    throw new DataNotVaidExeption("This login already exist");
                }
            }
        }
        if (user.getAge() < 0 || user.getAge() > Integer.MAX_VALUE) {
            throw new DataNotVaidExeption("Age not correct");
        }
        if (user.getLogin().length() < LOGIN_MIN_LENGHT) {
            throw new DataNotVaidExeption("Login must be longer than 6 characters");
        }
        if (user.getPassword().length() < USER_PWD_LENGHT) {
            throw new DataNotVaidExeption("Password must be longer than 6 characters");
        }
        if (user.getLogin() == null) {
            throw new DataNotVaidExeption("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new DataNotVaidExeption("Password can't be null");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new DataNotVaidExeption("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + USER_MIN_AGE);
        }
        return storageDao.add(user);

    }
}
