package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String EXISTING_LOGIN = "existingLogin";
    private static final String ANOTHER_PASSWORD = "anotherPassword";
    private static final String UNIQUE_LOGIN = "uniqueLogin";
    private static final String UNIQUE_PASSWORD = "uniquePassword";
    private static final String SHORT_LOGIN = "short";
    private static final String SHORT_PASSWORD = "short";
    private static final String EDGE_CASE_LENGTH = "length";
    private static final int VALID_AGE = 20;
    private static final int ANOTHER_AGE = 22;
    private static final int UNIQUE_AGE = 25;
    private static final int UNDERAGE = 17;

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullUserLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullUserPassword_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(VALID_AGE);
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullUserAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(null);
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(UNDERAGE);
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin(SHORT_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_edgeCaseLoginLength_ok() {
        User user = new User();
        user.setLogin(EDGE_CASE_LENGTH);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(SHORT_PASSWORD);
        user.setAge(VALID_AGE);
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_edgeCasePasswordLength_ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(EDGE_CASE_LENGTH);
        user.setAge(VALID_AGE);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin(EXISTING_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        Storage.people.add(user);

        User anotherUser = new User();
        anotherUser.setLogin(EXISTING_LOGIN);
        anotherUser.setPassword(ANOTHER_PASSWORD);
        anotherUser.setAge(ANOTHER_AGE);
        Assertions.assertThrows(RegistrationDataException.class,
                () -> registrationService.register(anotherUser));
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin(UNIQUE_LOGIN);
        user.setPassword(UNIQUE_PASSWORD);
        user.setAge(UNIQUE_AGE);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validUser_userAddedToStorage() {
        User user = new User();
        user.setLogin(UNIQUE_LOGIN);
        user.setPassword(UNIQUE_PASSWORD);
        user.setAge(UNIQUE_AGE);
        User registeredUser = registrationService.register(user);

        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
        Assertions.assertTrue(Storage.people.contains(user));
    }
}
