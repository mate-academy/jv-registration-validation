package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        user.setAge(18);
        user.setLogin("userLogin");
        user.setPassword("userPassword");
    }

    @Test
    void storageNotEmpty_Ok() {
        registrationService.register(user);
        boolean actual = people.isEmpty();
        assertFalse(actual);
    }

    @Test
    void userNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "Expected RuntimeException");
    }

   @Test
    void loginDuplicate_NotOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }

    @Test
    void loginEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }

    @Test
    void ageLessThan18_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }


    @Test
    void ageOverThan18_Ok() {
        user.setAge(19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void ageWithZero_NotOk() {
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }

    @Test
    void ageWithNegative_NotOk() {
        user.setAge(-18);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }

    @Test
    void ageWithNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }

    @Test
    void passwordLessThan6_NotOk() {
        user.setPassword("short");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }

    @Test
    void passwordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected RuntimeException");
    }
}
