package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static final String DEFAULT_LOGIN = "oleksii";
    private static final String DEFAULT_PASSWORD = "12121212";
    private static User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(27);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
    /*
    Testing the login field
     */

    @Test
    void register_loginDetailsAreCorrect_Ok() {
        user.setLogin(DEFAULT_LOGIN);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        User expected = storageDao.get(DEFAULT_LOGIN);
        assertEquals(expected, actual);

    }

    @Test
    void register_userAlreadyExists_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_emptyLineLogin_notOk() {
        user.setLogin("  ");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_firstLoginCharacterIsNotLetter_notOk() {
        user.setLogin("#oleksii");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
    /*
    Testing the password field
     */

    @Test
    void register_nullInPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_withEmptyFiled_notOk() {
        user.setPassword("  ");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_longPassword_notOk() {
        user.setPassword("1234567891011121314");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_smallPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
    /*
    Testing the age field
     */

    @Test
    void register_lowUserAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
}
