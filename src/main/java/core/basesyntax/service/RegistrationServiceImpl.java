package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao data = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!(user.getLogin() == null)
                && user.getPassword().length() >= 6
                && user.getAge() >= 18
                && !Objects.equals(data.get(user.getLogin()), user)) {
            data.add(user);
            return user;
        }
        throw new RuntimeException("Can't registration user");
    }
}
