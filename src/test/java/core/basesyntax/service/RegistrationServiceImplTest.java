package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User first;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        first = new User();
        first.setLogin("login1");
        first.setId(134L);
        first.setAge(19);
        first.setPassword("login13");
        Storage.people.clear();
    }

    @Test
    void passwordIsNull_NotOk() {
        first.setPassword(null);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(first),
                "An exception is expected if the password is null");
    }

    @Test
    void loginIsNull_NotOk() {
        first.setLogin(null);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(first),
                "An exception is expected if the login is null");
    }

    @Test
    void ageIsNull_NotOk() {
        first.setAge(0);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(first),
                "An exception is expected if the age is 0");
    }

    @Test
    void ageLessThan18_NotOk() {
        first.setAge(17);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(first),
                "An exception is expected if the age is less than 18");
    }

    @Test
    void ageIs18_Ok() {
        first.setLogin("login222");
        first.setAge(18);
        User result = registrationService.register(first);
        assertNotNull(result, "Registration should be successful when age is 18");
        assertEquals(first.getLogin(), result.getLogin(),
                "The registered user should have the same login as the input user");
    }

    @Test
    void register_existingLogin_notOk() {
        registrationService.register(first);
        User second = new User();
        second.setLogin("login1");
        second.setPassword("login14");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(second),
                "An exception is expected if the login already exists in the system");
    }

    @Test
    void loginIsLessThenSixCharacter_NotOk() {
        first.setLogin("log");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(first),
                "An exception is expected if the login is less than 6 characters long");
    }

    @Test
    void passwordIsLessThenSixCharacter_NotOk() {
        first.setPassword("pas");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(first),
                "An exception is expected if the password is less than 6 characters long");
    }

    @Test
    void registration_Ok() {
        User result = registrationService.register(first);
        assertNotNull(result, "Registration should be successful");
        assertEquals(first.getLogin(), result.getLogin(),
                "The registered user should have the same login as the input user");
    }
}
