package core.basesyntax.service;

import core.basesyntax.model.User;

public interface CheckUserService {
    Boolean checkNullRegister(User user);
    Boolean checkUserAge(User user);
    Boolean checkSameUserLogin(User user);
    Boolean checkUserPassword(User user);
}
