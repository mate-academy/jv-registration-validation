package core.basesyntax.service.validator;

import core.basesyntax.dao.StorageDao;

public interface LoginValidator {
    boolean isValid(String login, StorageDao storageDao);
}
