package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        return storageDao.add(user);
    }

    private void checkAge(User user){
        if(user.getAge() < MIN_AGE){
            throw new RuntimeException("You are to young!!!");
        }
    }

    private void checkUser(User user){
        if (user == null){
            throw new RuntimeException("User can't be null");
        }
    }

    private void checkPassword(User user){
        if (user.getPassword() == null){
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().isEmpty()){
            throw new RuntimeException("Password can not be Empty!!!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH){
            throw new RuntimeException("Your password is less then" + MIN_PASSWORD_LENGTH);
        }
    }

    private void checkLogin(User user){
        if (user.getLogin() == null){
            throw new RuntimeException("Login can't be null");
        }
        if (user.getLogin().isEmpty()){
            throw new RuntimeException("Login can not be Empty!!!");
        }
        if (storageDao.get(user.getLogin()) != null){
            throw new RuntimeException("User already exist in this Storage");
        }
    }
}
