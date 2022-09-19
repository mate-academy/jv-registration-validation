package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    protected static final int PASSWORD_MIN_LENGTH = 6;
    protected static final int AGE_BOUND = 18;
    private final StorageDao storageDao = new StorageDaoImpl();
    @Override
    public User register(User user) {
        if(user == null) {
            throw new RuntimeException("User is null.");
        }
        if(user.getLogin() == null){
            throw new RuntimeException("User login is null.");
        }
        if(user.getPassword() == null){
            throw new RuntimeException("Password login is null.");
        }
        if(user.getAge() == null){
            throw new RuntimeException("Age login is null.");
        }
        if(user.getAge() < 0){
            throw new RuntimeException("Age must be positive");
        }
        if(user.getAge() < AGE_BOUND){
            throw new RuntimeException("Age must be equal or more than " + AGE_BOUND + ".");
        }
        if(user.getPassword().length() < PASSWORD_MIN_LENGTH){
            throw new RuntimeException("Password length must be equal or more than " + PASSWORD_MIN_LENGTH + ".");
        }
        if(storageDao.get(user.getLogin()) != null ){
            throw new RuntimeException("User with login " + user.getLogin() + " is already exists in the database.");
        }
        return storageDao.add(user);
    }
}
