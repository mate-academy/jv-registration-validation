package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeAll
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void registerWith_correctUserData_ok() {
        User user1 = new User();
        user1.setAge(28);
        user1.setLogin("Vasiliy");
        user1.setPassword("0123459876");
        User user2 = new User();
        user2.setAge(56);
        user2.setLogin("Svitlana");
        user2.setPassword("qwerty123");
        User user3 = new User();
        user3.setAge(33);
        user3.setLogin("Penelopa");
        user3.setPassword("kruzlolqwer");
        User actualUser1 = registrationService.register(user1);
        User actualUser2 = registrationService.register(user2);
        User actualUser3 = registrationService.register(user3);
        assertEquals(user1, actualUser1);
        assertEquals(user2, actualUser2);
        assertEquals(user3, actualUser3);
    }

    @Test
    void registerWith_limitUserData_ok() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("Dmytro");
        user1.setPassword("asdfgh");
        User user2 = new User();
        user2.setAge(120);
        user2.setLogin("Larisa");
        user2.setPassword("123456");
        User user3 = new User();
        user3.setAge(18);
        user3.setLogin("Svyato");
        user3.setPassword("ytrewq");
        User actualUser1 = registrationService.register(user1);
        User actualUser2 = registrationService.register(user2);
        User actualUser3 = registrationService.register(user3);
        assertEquals(user1, actualUser1);
        assertEquals(user2, actualUser2);
        assertEquals(user3, actualUser3);
    }

    @Test
    void registerWith_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void registerWith_nullUserLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("0q9w8r7t6y");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_emptyUserLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("0q9w8r7t6y");
        user.setAge(36);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_shortUserLogin_notOk() {
        User user = new User();
        user.setLogin("Max");
        user.setPassword("0q9w8r7t6y");
        user.setAge(55);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_nullUserPassword_notOk() {
        User user1 = new User();
        user1.setLogin("Opanasiy");
        user1.setPassword(null);
        user1.setAge(78);
        User user2 = new User();
        user2.setLogin("Maximilian");
        user2.setPassword("");
        user2.setAge(25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void registerWith_shortUserPassword_notOk() {
        User user1 = new User();
        user1.setLogin("Katerina");
        user1.setPassword("0q4");
        user1.setAge(25);
        User user2 = new User();
        user2.setLogin("Katerina");
        user2.setPassword("0q4vb");
        user2.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void registerWith_tooOldUserAge_notOk() {
        User user = new User();
        user.setLogin("Agafiya");
        user.setPassword("0q9w8r7t6y");
        user.setAge(125);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_tooYoungUserAge_notOk() {
        User user = new User();
        user.setLogin("Ostap4ik");
        user.setPassword("0q9w8r7t6y");
        user.setAge(12);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "The user must be at least 18 years of age. Registration is not possible");
    }

    @Test
    void registerWith_negativeUserAge_notOk() {
        User user = new User();
        user.setLogin("Oksana");
        user.setPassword("0q9w8r7t6y");
        user.setAge(-22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You can not set the user a negative age value. Registration is not possible");
    }

    @Test
    void registerWith_nullUserAge_notOk() {
        User user = new User();
        user.setLogin("Oksana");
        user.setPassword("0q9w8r7t6y");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_containedInDbUser_notOk() {
        User user = new User();
        user.setLogin("Anhelina");
        user.setPassword("0q9w8r7t6y");
        user.setAge(35);
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
