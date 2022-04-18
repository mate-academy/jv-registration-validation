package core.basesyntax;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("userLogin");
        user.setPassword("666666");
    }

    @AfterEach
    void storageClose() {
        people.clear();
    }

    @Test
    void register_userNull_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_5characters_notOk() {
        user.setPassword("5symb");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_10characters_ok() {
        user.setPassword("10_symbols");
        boolean actual = user == registrationService.register(user);
        assertTrue(actual);
    }

    @Test
    void userAgeIs17_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeIs18_ok() {
        user.setAge(18);
        boolean actual = user == registrationService.register(user);
        assertTrue(actual);
    }

    @Test
    void userAgeIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginAlreadyExist_notOk() {
        StorageDaoImpl testStorageData = new StorageDaoImpl();
        user.setLogin("Login");
        testStorageData.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginIsUnique_ok() {
        user.setLogin("Unique_Login");
        boolean actual = user == registrationService.register(user);
        assertTrue(actual);
    }

    @Test
    void userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
