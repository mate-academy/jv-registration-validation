package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public User register(User user) {

        if (user != null && user.getAge() != null
                && user.getLogin() != null && user.getPassword() != null) {
            StorageDaoImpl storage = new StorageDaoImpl();
            if (storage.get(user.getLogin()) == null) {
                if (user.getAge() >= 18 && user.getPassword().length() >= 6) {
                    storage.add(user);
                    return user;
                }
            }
        }
        return null;
    }
}
