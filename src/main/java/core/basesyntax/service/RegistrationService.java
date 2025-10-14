package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public interface RegistrationService {

    StorageDaoImpl dao = new StorageDaoImpl();
    User register(User user);
}
