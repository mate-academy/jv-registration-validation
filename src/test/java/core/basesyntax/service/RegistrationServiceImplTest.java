package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        storageDao.add(new User(1L, "pavlo_ivanets", "458984", 18));
        storageDao.add(new User(2L, "katerynka", "fks2045", 24));
    }

    @Test
    void register_LoginCorrectLength_Ok() throws RegistrationException {
        User user1 = new User(4L, "kate43", "tyo7654", 25);
        User user2 = new User(4L, "kate4305", "tyo7654", 25);
        User actual1 = registrationService.register(user1);
        User actual2 = registrationService.register(user2);
        assertEquals(user1, actual1);
        assertEquals(user2, actual2);
    }

    @Test
    void register_LoginShorterThan6_NotOk() {
        try {
            registrationService.register(new User(5L, "cat", "goi32kls", 50));
            registrationService.register(new User(5L, "sofia", "4221dls", 20));
        } catch (RegistrationException e) {
            return;
        }
        fail("Registration exception should be thrown if trying to register user "
                + "with login shorter then 6 symbols.");
    }

    @Test
    void register_LoginNull_NotOk() {
        try {
            registrationService.register(new User(3L, null, "itk567e3", 29));
        } catch (RegistrationException e) {
            return;
        }
        fail("Registration exception should be thrown if trying to register user "
                + "with login shorter then 6 symbols.");
    }

    @Test
    void register_ExistingUser_NotOk() {
        try {
            registrationService.register(new User(3L, "katerynka", "jhgdss", 19));
            registrationService.register(new User(1L, "pavlo_ivanets", "458984", 18));
        } catch (RegistrationException e) {
            return;
        }
        fail("Registration exception should be thrown if trying to register user with already "
                + "existing login.");
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        try {
            registrationService.register(new User(32L, "user195", null, 18));
        } catch (RegistrationException e) {
            return;
        }
        fail("Registration exception should be thrown if trying to register user "
                + "with password shorter then 6 symbols.");
    }

    @Test
    void register_PasswordShorterThan6_NotOk() {
        try {
            registrationService.register(new User(3L, "borys1995", "543", 29));
            registrationService.register(new User(9L, "zhenya_kit", "hello", 54));
        } catch (RegistrationException e) {
            return;
        }
        fail("Registration exception should be thrown if trying to register user "
                + "with password shorter then 6 symbols.");
    }

    @Test
    void register_PasswordCorrectLength_Ok() throws RegistrationException {
        User user1 = new User(15L, "yurii_sk", "546987", 21);
        User user2 = new User(10L, "oleksii", "ogsr7654", 29);
        User actual1 = registrationService.register(user1);
        User actual2 = registrationService.register(user2);
        assertEquals(user1, actual1);
        assertEquals(user2, actual2);
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        try {
            registrationService.register(new User(14L, "user1692", "954leels", 2));
            registrationService.register(new User(34L, "dog453", "password", 17));
        } catch (RegistrationException e) {
            return;
        }
        fail("Registration exception should be thrown if trying to register user "
                + "with age less than 18.");
    }

    @Test
    void register_AgeCorrect_Ok() throws RegistrationException {
        User user1 = new User(71L, "igor_trs", "546987", 21);
        User user2 = new User(52L, "taras85", "ogsr7654", 29);
        User actual1 = registrationService.register(user1);
        User actual2 = registrationService.register(user2);
        assertEquals(user1, actual1);
        assertEquals(user2, actual2);
    }
}
