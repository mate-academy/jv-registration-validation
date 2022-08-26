package core.basesyntax.service;

import core.basesyntax.model.User;

public interface CheckUserService {
    Boolean checkNullUser(User user);

    Boolean checkUserAge(User user);

    Boolean checkUserLogin(User user);

    Boolean checkUserPassword(User user);
}
