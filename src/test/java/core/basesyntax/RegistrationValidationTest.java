package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationValidationTest {
    private final StorageDao storage = new StorageDaoImpl();
    private RegistrationService registrationService;

    //region Repeated Tasks
    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
    //endregion

    //region assertEquals & assertNotNull Tests
    @Test
    void register_validUser_Ok() {
        User validUser = new User();
        validUser.setAge(18);
        validUser.setLogin("validLogin");
        validUser.setPassword("validPassword");
        User actual = registrationService.register(validUser);
        assertEquals(actual, validUser);
        User retrievedUser = storage.get(validUser.getLogin());
        assertNotNull(retrievedUser);
    }
    //endregion

    //region assertThrows Tests
    @Test
    void register_invalidUser_notOk() {
        User invalidAgeUser = new User();
        invalidAgeUser.setAge(17);
        invalidAgeUser.setLogin("validName");
        invalidAgeUser.setPassword("validPassword");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAgeUser));

        User invalidLoginUser = new User();
        invalidLoginUser.setAge(18);
        invalidLoginUser.setLogin("");
        invalidLoginUser.setPassword("validPassword");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidLoginUser));

        User invalidPasswordUser = new User();
        invalidPasswordUser.setAge(19);
        invalidPasswordUser.setLogin("validName");
        invalidPasswordUser.setPassword("abc");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidPasswordUser));
    }

    @Test
    void register_existingUser_notOk() {
        User user = new User();
        user.setLogin("userTest");
        Storage.people.add(user);
        User userLoginCopy = new User();
        userLoginCopy.setAge(Integer.MAX_VALUE);
        userLoginCopy.setLogin(user.getLogin());
        userLoginCopy.setPassword("validPassword");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userLoginCopy));
    }

    @Test
    void register_ageNotEligible_notOk() {
        User user = new User();
        user.setAge(0);
        user.setLogin("userValid");
        user.setPassword("password");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginShorterThan6_notOk() {
        User user = new User();
        user.setAge(55);
        user.setLogin("user1");
        user.setPassword("password");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordShorterThan6_notOk() {
        User user = new User();
        user.setAge(50);
        user.setLogin("userValid");
        user.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("userValid");
        user.setPassword("abc123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(99);
        user.setLogin(null);
        user.setPassword("abc123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setAge(22);
        user.setLogin("user123");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    //endregion
}
