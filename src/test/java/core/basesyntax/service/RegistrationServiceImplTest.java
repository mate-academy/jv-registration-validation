package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.UserExistsException;
import core.basesyntax.service.exception.UserValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static final String FIRST_LOGIN = "FIRST_LOGIN";
    private static final String SECOND_LOGIN = "SECOND_LOGIN";
    private static final String THIRD_LOGIN = "THIRD_LOGIN";
    private static final String FOURTH_LOGIN = "FOURTH_LOGIN";
    private static final String FIFTH_LOGIN = "FIFTH_LOGIN";
    private static final String SIXTH_LOGIN = "SIXTH_LOGIN";
    private static final String SEVENTH_LOGIN = "SEVENTH_LOGIN";
    private static final int MIN_VALID_AGE = 18;
    private static final int VALID_AGE = 60;
    private static final int MAX_INVALID_AGE = 17;
    private static final String VALID_PASSWORD = "123456";
    private static final String INVALID_PASSWORD = "12345";

    @BeforeAll
    public static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin(FIRST_LOGIN);
        validUser.setAge(MIN_VALID_AGE);
        validUser.setPassword(VALID_PASSWORD);
        service.register(validUser);
        assertTrue(Storage.people.contains(validUser),
                "Test failed! Expected storage people contains "
                + validUser + " = true, but was false");
    }

    @Test
    public void register_validUser_sizeIncreased_ok() {
        User validUser = new User();
        validUser.setLogin(SEVENTH_LOGIN);
        validUser.setAge(MIN_VALID_AGE);
        validUser.setPassword(VALID_PASSWORD);
        int expectedSize = Storage.people.size() + 1;
        service.register(validUser);
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, Storage.people.size(), "Test failed!"
                + " Size of storage after adding valid user should be "
                + expectedSize + ", but was "
                + actualSize
        );
    }

    @Test
    public void register_loginIsNull_notOk() {
        User nullLoginUser = new User();
        nullLoginUser.setLogin(null);
        nullLoginUser.setAge(MIN_VALID_AGE);
        nullLoginUser.setPassword(VALID_PASSWORD);
        assertThrows(UserValidationException.class, () -> service.register(nullLoginUser));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User originalUser = new User();
        originalUser.setLogin(THIRD_LOGIN);
        originalUser.setAge(MIN_VALID_AGE);
        originalUser.setPassword(VALID_PASSWORD);
        User duplicateLoginUser = new User();
        duplicateLoginUser.setLogin(THIRD_LOGIN);
        duplicateLoginUser.setAge(MIN_VALID_AGE);
        duplicateLoginUser.setPassword(VALID_PASSWORD);
        service.register(originalUser);
        assertThrows(UserExistsException.class, () -> service.register(duplicateLoginUser));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        User nullPassUser = new User();
        nullPassUser.setLogin(SECOND_LOGIN);
        nullPassUser.setAge(MIN_VALID_AGE);
        nullPassUser.setPassword(null);
        assertThrows(UserValidationException.class, () -> service.register(nullPassUser));
    }

    @Test
    public void register_passwordLessThan6CharsLong_notOk() {
        User invPassUser = new User();
        invPassUser.setLogin(FIFTH_LOGIN);
        invPassUser.setAge(MIN_VALID_AGE);
        invPassUser.setPassword(INVALID_PASSWORD);
        assertThrows(UserValidationException.class, () -> service.register(invPassUser));
    }

    @Test
    public void register_ageLessThan18_notOk() {
        User invAgeUser = new User();
        invAgeUser.setLogin(FOURTH_LOGIN);
        invAgeUser.setAge(MAX_INVALID_AGE);
        invAgeUser.setPassword(VALID_PASSWORD);
        assertThrows(UserValidationException.class, () -> service.register(invAgeUser));
    }

    @Test
    public void register_ageIsGreaterThan18_ok() {
        User validUser = new User();
        validUser.setLogin(SIXTH_LOGIN);
        validUser.setAge(VALID_AGE);
        validUser.setPassword(VALID_PASSWORD);
        service.register(validUser);
        assertTrue(Storage.people.contains(validUser),
                "Test failed! Expected storage people contains "
                        + validUser + " = true, but was false");
    }
}
