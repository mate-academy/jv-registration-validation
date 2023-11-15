package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.IncorrectDataException;
import core.basesyntax.model.User;

public class UserValidator {
    private static final StorageDaoImpl storageDao = new StorageDaoImpl();

    public boolean validate(User user) throws IncorrectDataException {
        if (user.getId() == null
                || user.getId() < 0
                || user.getId() > Long.MAX_VALUE - 1) {
            throw new IncorrectDataException("Invalid ID.");
        }
        if (user.getAge() == null
                || user.getAge() < 18
                || user.getAge() > 150) {
            throw new IncorrectDataException("Invalid Age.");
        }
        if (user.getLogin() == null
                || Character.toString(user.getLogin().charAt(0))
                .matches("[^a-z|A-Z]")
                || user.getLogin().length() < 7
                || storageDao.get(user.getLogin()) != null) {
            throw new IncorrectDataException("Invalid Login.");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < 7) {
            throw new IncorrectDataException("Invalid Password.");
        }
        return true;
    }
}
