package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static Storage storage;
    private static RegistrationServiceImpl registrationService;

    private User firstTestUser;
    private User secondTestUser;
    private String userLoginTest;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
    }

    @BeforeEach
    void setUp() {
        firstTestUser = new User();
        firstTestUser.setAge(18);
        firstTestUser.setLogin("BobLogin");
        firstTestUser.setPassword("BobPas123");

        secondTestUser = new User();
        secondTestUser.setAge(100);
        secondTestUser.setLogin("JackLogin");
        secondTestUser.setPassword("JackPas123");

    }

    @Test
    void nullUser_Is_Not_Ok() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
        int expected = 0;
        assertEquals(expected, storage.people.size());
    }

    @Test
    void password_User_Is_Not_Ok() {
        firstTestUser.setPassword("Bob space");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));
        firstTestUser.setPassword("mini");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));
        firstTestUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));

    }

    @Test
    void password_User_Is_Ok() {
        registrationService.register(firstTestUser);
        int expected = 2;
        registrationService.register(secondTestUser);
        assertEquals(expected, storage.people.size());
    }

    @Test
    void age_User_Is_Not_Ok() {
        firstTestUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));
        firstTestUser.setAge(101);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));

    }

    @Test
    void age_User_Is_Ok() {
        int expected = 1;
        registrationService.register(firstTestUser);
        assertEquals(expected, storage.people.size());
        expected = 2;
        registrationService.register(secondTestUser);
        assertEquals(expected, storage.people.size());
    }

    @Test
    void login_User_Is_Not_Ok() {
        userLoginTest = "BobLogin space";
        firstTestUser.setLogin(userLoginTest);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));
        firstTestUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));

    }

    @Test
    void login_User_Is_Ok() {
        userLoginTest = "loginB";
        firstTestUser.setLogin(userLoginTest);
        registrationService.register(firstTestUser);
        int expected = 1;
        assertEquals(expected, storage.people.size());
    }

    @Test
    void such_login_User_Is_Not_Ok() {
        userLoginTest = "secondD";
        firstTestUser.setLogin(userLoginTest);
        int expected = 1;
        registrationService.register(firstTestUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstTestUser));
        assertEquals(expected, storage.people.size());
    }

    @AfterEach
    void afterEach() {
        registrationService = new RegistrationServiceImpl();
        storage.people.clear();
    }
}
