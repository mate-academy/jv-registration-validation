package core.basesyntax.validator;

import core.basesyntax.model.User;

public interface Validator {
    boolean isValid(User user);
}
