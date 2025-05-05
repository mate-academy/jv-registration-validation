package core.basesyntax.validation;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserValidatorImplTest {
    private final UserValidator validator = new UserValidatorImpl();

    @Test
    public void validate_ValidUser_ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin("valid1");
        user.setPassword("valid1");
        Assertions.assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    public void validate_UserIsNull_notOk() {
        User user = null;
        Assertions.assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_LoginIsNull_notOk() {
        User user = new User();
        user.setLogin(null);
        Assertions.assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_LoginIsLessThan6Characters_notOk() {
        User user = new User();
        user.setLogin("null1");
        Assertions.assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_PasswordIsNull_notOk() {
        User user = new User();
        user.setPassword(null);
        Assertions.assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_PasswordIsLessThan6Characters_notOk() {
        User user = new User();
        user.setPassword("null1");
        Assertions.assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_AgeIsNull_notOk() {
        User user = new User();
        user.setAge(null);
        Assertions.assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_AgeIsLessThan18YearsOld_notOk() {
        User user = new User();
        user.setAge(17);
        Assertions.assertThrows(UserValidationException.class, () -> validator.validate(user));
    }
}
