package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.UserExistsException;
import core.basesyntax.service.exception.UserValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static final String DEFAULT_LOGIN = "DEFAULT_LOGIN";
    private static final String FIRST_LOGIN = "FIRST_LOGIN";
    private static final String SECOND_LOGIN = "SECOND_LOGIN";
    private static final String THIRD_LOGIN = "THIRD_LOGIN";
    private static final String FOURTH_LOGIN = "FOURTH_LOGIN";
    private static final String FIFTH_LOGIN = "FIFTH_LOGIN";
    private static final String SIXTH_LOGIN = "SIXTH_LOGIN";
    private static final String DUPLICATE_LOGIN = "DUPLICATE_LOGIN";
    private static final int MIN_VALID_AGE = 18;
    private static final int VALID_AGE = 60;
    private static final int MAX_INVALID_AGE = 17;
    private static final String VALID_PASSWORD = "123456";
    private static final String INVALID_PASSWORD = "12345";
    private User defaultUser;

    @BeforeAll
    public static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        defaultUser = new User();
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(VALID_PASSWORD);
        defaultUser.setAge(MIN_VALID_AGE);
    }

    @Test
    public void register_validUser_ok() {
        service.register(defaultUser);
        assertTrue(Storage.people.contains(defaultUser),
                "Test failed! Expected storage people contains "
                + defaultUser + " = true, but was false");
    }

    @Test
    public void register_validUser_returnedEqualUser_ok() {
        defaultUser.setLogin(THIRD_LOGIN);
        User expectedUser = defaultUser;
        User actualUser = service.register(defaultUser);
        assertEquals(expectedUser, actualUser, "Test failed!"
                + "Expected return value " + expectedUser
                + ", but was " + actualUser);
    }

    @Test
    public void register_validUser_sizeIncreased_ok() {
        defaultUser.setLogin(FIRST_LOGIN);
        int expectedSize = Storage.people.size() + 1;
        service.register(defaultUser);
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, Storage.people.size(), "Test failed!"
                + " Size of storage after adding valid user should be "
                + expectedSize + ", but was "
                + actualSize
        );
    }

    @Test
    public void register_loginIsNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(UserValidationException.class, () -> service.register(defaultUser));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        defaultUser.setLogin(DUPLICATE_LOGIN);
        service.register(defaultUser);
        assertThrows(UserExistsException.class, () -> service.register(defaultUser));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        defaultUser.setLogin(SECOND_LOGIN);
        defaultUser.setPassword(null);
        assertThrows(UserValidationException.class, () -> service.register(defaultUser));
    }

    @Test
    public void register_passwordLessThan6CharsLong_notOk() {
        defaultUser.setLogin(FOURTH_LOGIN);
        defaultUser.setPassword(INVALID_PASSWORD);
        assertThrows(UserValidationException.class, () -> service.register(defaultUser));
    }

    @Test
    public void register_ageLessThan18_notOk() {
        defaultUser.setLogin(FIFTH_LOGIN);
        defaultUser.setAge(MAX_INVALID_AGE);
        assertThrows(UserValidationException.class, () -> service.register(defaultUser));
    }

    @Test
    public void register_ageIsGreaterThan18_ok() {
        defaultUser.setLogin(SIXTH_LOGIN);
        defaultUser.setAge(VALID_AGE);
        service.register(defaultUser);
        assertTrue(Storage.people.contains(defaultUser),
                "Test failed! Expected storage people contains "
                        + defaultUser + " = true, but was false");
    }
}
