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
    private User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        actual = new User("someLogin", "holyMoly", 20);
    }

    @Test
    void userDuplicate_notOk() {
        User duplicate = registrationService.register(actual);
        assertThrows(RuntimeException.class, () -> registrationService.register(duplicate));
    }

    @Test
    void userRegistration_Ok() {
        assertEquals(actual,registrationService.register(actual));
    }

    @Test
    void nullUser_notOk(){
        actual = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void nullUserLogin_notOk() {
        actual.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void nullUserAge_notOk() {
        actual.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void nullUserPass_notOk() {
        actual.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void userAge_notOk() {
        actual.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void userPassword_notOk() {
        actual.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @AfterEach
    void storageClear() {
        Storage.people.clear();
    }
}
