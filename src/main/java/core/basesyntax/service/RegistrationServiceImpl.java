package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String LOGIN_EXIST = "user with such login already exists";
    private static final String LOGIN_IS_NULL = "user's login is null";
    private static final String USER_IS_NULL = "user is null";
    private static final String TOO_YOUNG = "the user must first grow up a little, age less than 18";
    private static final String LOGIN_HAS_INVALID_SYMBOL = "login has at least one invalid symbols";
    private static final String LOGIN_HAS_LESS_THAT_SIX_SYMBOLS = "login too short, must be at least six symbols";
    private static final String SPECIAL_SYMBOL = "\\W+";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException(USER_IS_NULL);
        }
        if (user.getLogin() == null) {
            throw new RuntimeException(LOGIN_IS_NULL);
        }
        if (user.getAge() < 18) {
            throw new RuntimeException(TOO_YOUNG);
        }
        if (user.getLogin().length() < 6) {
            throw new RuntimeException(LOGIN_HAS_LESS_THAT_SIX_SYMBOLS);
        }
        if (user.getLogin().replaceAll(SPECIAL_SYMBOL, "").length() - user.getLogin().length() != 0) {
            throw new RuntimeException(LOGIN_HAS_INVALID_SYMBOL);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException(LOGIN_EXIST);
        }
        return storageDao.add(user);
    }
}
