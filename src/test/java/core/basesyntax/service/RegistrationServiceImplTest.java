package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_LoginCorrectLength_Ok() throws RegistrationException {
        User user = new User("kate43", "tyo7654", 25);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginShorterThan6_NotOk() {
        User user = new User("cat", "goi32kls", 50);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration exception should be thrown if trying to register user "
                        + "with login shorter then 6 symbols.");
    }

    @Test
    void register_LoginNull_NotOk() {
        User user = new User(null, "itk567e3", 29);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration exception should be thrown if trying to register user "
                        + "with login shorter then 6 symbols.");
    }

    @Test
    void register_ExistingUser_NotOk() {
        User user = new User("katerynka", "jhgdss", 19);
        storageDao.add(user);
        User newUser = new User("katerynka", "jhgdsssa", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Registration exception should be thrown if trying to register user "
                        + "with already existing login.");
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        User user = new User("user195", null, 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration exception should be thrown if trying to register user "
                        + "with password shorter then 6 symbols.");
    }

    @Test
    void register_PasswordShorterThan6_NotOk() {
        User user = new User("borys1995", "543", 29);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration exception should be thrown if trying to register user "
                        + "with password shorter then 6 symbols.");
    }

    @Test
    void register_PasswordCorrectLength_Ok() throws RegistrationException {
        User user = new User("yurii_sk", "546987", 21);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        User user = new User("user1692", "954leels", 2);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration exception should be thrown if trying to register user "
                        + "with age less than 18.");
    }

    @Test
    void register_AgeCorrect_Ok() throws RegistrationException {
        User user = new User("igor_trs", "546987", 21);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
