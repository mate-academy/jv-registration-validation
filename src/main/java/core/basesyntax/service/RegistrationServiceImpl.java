package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String IS_REGISTERED_MESSAGE =
            "User with such login is already registered";
    private static final String USER_IS_NULL_MESSAGE = "User can't be null";
    private final StorageDao storageDao = new StorageDaoImpl();
    private final FieldValidator<String> stringFieldValidator = new UserStringFieldsValidator();
    private final FieldValidator<Integer> integerFieldValidator = new UserIntegerFieldValidator();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException(USER_IS_NULL_MESSAGE);
        }
        String login = user.getLogin();
        stringFieldValidator.validate(login, UserFields.LOGIN);
        stringFieldValidator.validate(user.getPassword(), UserFields.PASSWORD);
        integerFieldValidator.validate(user.getAge(), UserFields.AGE);
        if (isRegistered(login)) {
            throw new RuntimeException(IS_REGISTERED_MESSAGE);
        }
        return storageDao.add(user);
    }

    private boolean isRegistered(String login) {
        return storageDao.get(login) != null;
    }
}
