package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE_OF_USERS=18;
    private static final int MIN_LENGTH_OF_PASSWORD=6;

    @Override
    public User register(User user) {
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        storageDao.add(user);
        return user;
    }

    public static void checkPassword(User user){
        if(user.getPassword()==null){
            throw new NullPointerException("Password cannot be null");
        }
        if(user.getPassword().length()<MIN_LENGTH_OF_PASSWORD){
            throw new RuntimeException("Minimal length of password is "+MIN_LENGTH_OF_PASSWORD);
        }
    }

    public static void checkAge(User user){
        if(user.getAge()==null){
            throw  new NullPointerException("Age connot be null");
        }
        if(user.getAge()<MIN_AGE_OF_USERS){
            throw new RuntimeException("Minimum user age for registration:"+ MIN_AGE_OF_USERS+" . Your age is not suitable");
        }
    }

    public static void checkLogin(User user){
        if(user.getLogin()==null){
            throw new NullPointerException("Login cannot be null");
        }
        if(user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User " + user.getLogin() + " alreasy exist");
        }
    }
}
