package core.basesyntax.service.validator;

import core.basesyntax.dao.StorageDao;

public class LoginValidatorImpl implements LoginValidator {
    @Override
    public boolean isValid(String login, StorageDao storageDao) {
        return login != null && storageDao.get(login) == null;
    }
}
