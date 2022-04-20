package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User userFirst;
    private static User userSecond;
    private static User userThird;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userFirst = new User("DefaultLogin1", "DefaultPassword1", 20);
        userSecond = new User("DefaultLogin2", "DefaultPassword2", 29);
        userThird = new User("DefaultLogin3", "DefaultPassword3", 80);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registrationNullField_notOk() {
        userFirst.setLogin(null);
        userSecond.setPassword(null);
        userThird.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userFirst));
        assertThrows(RuntimeException.class, () -> registrationService.register(userSecond));
        assertThrows(RuntimeException.class, () -> registrationService.register(userThird));
    }

    @Test
    void registrationValidField_Ok() {
        assertEquals(userFirst, registrationService.register(userFirst));
        assertEquals(userSecond, registrationService.register(userSecond));
        assertEquals(userThird, registrationService.register(userThird));
        assertTrue(Storage.people.contains(userFirst));
        assertTrue(Storage.people.contains(userSecond));
        assertTrue(Storage.people.contains(userThird));
    }

    @Test
    void registrationSameLogin_Ok() {
        userFirst.setLogin("SameLogin");
        userSecond.setLogin("SameLogin");
        assertEquals(userFirst, registrationService.register(userFirst));
        assertThrows(RuntimeException.class, () -> registrationService.register(userSecond));
        assertFalse(Storage.people.contains(userSecond));
    }

    @Test
    void registrationIncorrectAge_notOk() {
        userFirst.setAge(17);
        userSecond.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(userFirst));
        assertThrows(RuntimeException.class, () -> registrationService.register(userSecond));
    }

    @Test
    void registrationInvalidPassword_notOk() {
        userFirst.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(userFirst));
    }

    @Test
    void registrationNullUser_notOk() {
        userFirst = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(userFirst));
    }
}
