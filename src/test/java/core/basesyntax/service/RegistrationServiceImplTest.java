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
    void register_user_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_withNull_notOk() {
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
    void register_checkForAbsenceInTheFirstLetterOfCharacter_notOk() {
        user.setLogin("#oleksii");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
    /*
    Testing the password field
     */

    @Test
    void register_withCorrectPassword_ok() {
        user.setPassword(DEFAULT_PASSWORD);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_withNullInPassword_notOk() {
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
    void register_withVeryLongPassword_notOk() {
        user.setPassword("1234567891011121314");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_withVerySmallPassword_notOk() {
        user.setPassword("12");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
    /*
    Testing the age field
     */

    @Test
    void register_underageUser_notOk() {
        user.setAge(12);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_veryOldUser_notOk() {
        user.setAge(90);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
}
