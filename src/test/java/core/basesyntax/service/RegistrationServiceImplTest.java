package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User expectedUser;
    private static final String AGE_ERROR_MESSAGE = "Age must be equals or more than 18";
    private static final String PASSWORD_ERROR_MESSAGE = "Password must have 6 or more characters";
    private static final String FIELD_NULL_ERROR = "This field can't be \"null\"";
    private static final String USER_NULL_ERROR = "Error, user can't be null";
    private static final String EXIST_USER_ERROR = "User with this login already exist";

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        expectedUser = new User();
        expectedUser.setLogin("login");
        expectedUser.setPassword("password");
        expectedUser.setAge(18);
    }

    @Test
    void register_nullUser_notOk() {
        expectedUser = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                USER_NULL_ERROR);
    }

    @Test
    void register_nullLogin_notOk() {
        expectedUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                FIELD_NULL_ERROR);
    }

    @Test
    public void register_nullAge_notOk() {
        expectedUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                FIELD_NULL_ERROR);
    }

    @Test
    void register_nullPassword_notOk() {
        expectedUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                FIELD_NULL_ERROR);
    }

    @Test
    void register_existedUser_notOk() {
        User newUser = registrationService.register(expectedUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser),
                EXIST_USER_ERROR);
    }

    @Test
    void register_notExistedUser_Ok() {
        assertEquals(expectedUser,registrationService.register(expectedUser));
    }

    @Test
    void register_notValidAge_notOk() {
        expectedUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                AGE_ERROR_MESSAGE);
        expectedUser.setAge(-23);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                AGE_ERROR_MESSAGE);
        expectedUser.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                AGE_ERROR_MESSAGE);
    }

    @Test
    void register_notValidPassword_notOk() {
        expectedUser.setPassword("qwe");
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                PASSWORD_ERROR_MESSAGE);
        expectedUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                PASSWORD_ERROR_MESSAGE);
        expectedUser.setPassword("11111");
        assertThrows(RuntimeException.class, () -> registrationService.register(expectedUser),
                PASSWORD_ERROR_MESSAGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
