package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);
    StorageDaoImpl dao = new StorageDaoImpl();
}
