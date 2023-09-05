package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NotValidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "CheckLogin";
    private static final String VALID_PASSWORD = "CheckPassword";
    private static final int VALID_AGE = 18;
    private static RegistrationService registrationService;
    private final User user = new User();

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setDefaultUser() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_User_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(NotValidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_ExistsUser_notOk() {
        Storage.PEOPLE.add(user);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_notOk() {
        user.setLogin(null);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectLogin_notOk() {
        user.setLogin("login");
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_notOk() {
        user.setPassword(null);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectPassword_notOk() {
        user.setPassword("1234");
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_notOk() {
        user.setAge(null);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectAge_notOk() {
        user.setAge(14);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }
}
