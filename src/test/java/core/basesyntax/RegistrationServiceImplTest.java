package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void passed() {
        validUser = new User();
        validUser.setAge(25);
        validUser.setLogin("Anna");
        validUser.setPassword("111111");
        storageDao.add(validUser);
    }

    @Test
    void register_nullUser_isNotOk() {
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(null),
                "Should throw InvalidUserDataException when user is null");
    }

    @Test
    void register_shortLogin_isNotOk() {
        User user = new User();
        user.setLogin("u");
        user.setPassword("password");
        user.setAge(20);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Should throw InvalidUserDataException when login is short");
    }

    @Test
    void register_nullLogin_isNotOk() {
        User user = new User();
        user.setLogin("Qwerty");
        user.setPassword("111111");
        user.setAge(null);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Should throw InvalidUserDataException when login is null");
    }

    @Test
    void register_shortPassword_isNotOk() {
        User user = new User();
        user.setLogin("uhwert");
        user.setPassword("p");
        user.setAge(20);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Should throw InvalidUserDataException when password is short");
    }

    @Test
    void register_nullPassword_isNotOk() {
        User user = new User();
        user.setLogin("Qwerty");
        user.setPassword(null);
        user.setAge(20);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Should throw InvalidUserDataException when password is null");
    }

    @Test
    void register_ageBelowThreshold_isNotOk() {
        User user = new User();
        user.setLogin("uhwerty");
        user.setPassword("password");
        user.setAge(17);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Should throw InvalidUserDataException when age is under 18");
    }

    @Test
    void register_nullAge_isNotOk() {
        User user = new User();
        user.setLogin("Qwerty");
        user.setPassword("Qwerty1");
        user.setAge(null);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Should throw InvalidUserDataException when age is null");
    }

    @Test
    public void register_duplicateUser_isOk() {
        Storage.people.add(validUser);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(validUser),
                "Should throw InvalidUserDataException duplicate users were detected");
        assertEquals(1, Storage.people.size());
    }
}
