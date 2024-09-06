package core.basesyntax.validation;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserValidatorImplTest {
    private final UserValidator validator = new UserValidatorImpl();

    @Test
    public void validate_ValidUser_ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin("validated");
        user.setPassword("validated");
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    public void validate_UserIsNull_notOk() {
        User user = null;
        assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_LoginIsNull_notOk() {
        User user = new User();
        user.setLogin(null);
        assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_LoginIsLessThan6Characters_notOk() {
        User user = new User();
        user.setLogin("null");
        assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_PasswordIsNull_notOk() {
        User user = new User();
        user.setPassword(null);
        assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_PasswordIsLessThan6Characters_notOk() {
        User user = new User();
        user.setPassword("null");
        assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_AgeIsNull_notOk() {
        User user = new User();
        user.setAge(null);
        assertThrows(UserValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void validate_AgeIsLessThan18YearsOld_notOk() {
        User user = new User();
        user.setAge(17);
        assertThrows(UserValidationException.class, () -> validator.validate(user));
    }
}