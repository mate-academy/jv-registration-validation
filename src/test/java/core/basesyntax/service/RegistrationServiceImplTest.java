package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.DbContainsSuchUserException;
import core.basesyntax.exception.UserEmptyOrNullLoginException;
import core.basesyntax.exception.UserIsNullException;
import core.basesyntax.exception.UserLoginLengthException;
import core.basesyntax.exception.UserMinimumAgeException;
import core.basesyntax.exception.UserNullAgeException;
import core.basesyntax.exception.UserNullOrEmptyPasswordException;
import core.basesyntax.exception.UserPasswordLengthException;
import core.basesyntax.exception.UserTooOldAgeException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeAll
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void registerWith_correctUserData_ok(List<User> users) {
        Storage.people.clear();
        for (User user : users) {
            User actualUser = registrationService.register(user);
            assertEquals(user, actualUser);
        }
    }

    @Test
    void registerWith_nullUser_notOk() {
        assertThrows(UserIsNullException.class,() -> registrationService.register(null));
    }

    @Test
    void registerWith_nullUserLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("0q9w8r7t6y");
        user.setAge(25);
        assertThrows(UserEmptyOrNullLoginException.class,() -> registrationService.register(user));
    }

    @Test
    void registerWith_emptyUserLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("0q9w8r7t6y");
        user.setAge(36);
        assertThrows(UserEmptyOrNullLoginException.class,() -> registrationService.register(user));
    }

    @Test
    void registerWith_shortUserLogin_notOk() {
        User user = new User();
        user.setLogin("Max");
        user.setPassword("0q9w8r7t6y");
        user.setAge(55);
        assertThrows(UserLoginLengthException.class,() -> registrationService.register(user));
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
        assertThrows(UserNullOrEmptyPasswordException.class,
                () -> registrationService.register(user1));
        assertThrows(UserNullOrEmptyPasswordException.class,
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
        assertThrows(UserPasswordLengthException.class,() -> registrationService.register(user1));
        assertThrows(UserPasswordLengthException.class,() -> registrationService.register(user2));
    }

    @Test
    void registerWith_tooOldUserAge_notOk() {
        User user = new User();
        user.setLogin("Agafiya");
        user.setPassword("0q9w8r7t6y");
        user.setAge(125);
        assertThrows(UserTooOldAgeException.class,() -> registrationService.register(user));
    }

    @Test
    void registerWith_tooYoungUserAge_notOk() {
        User user = new User();
        user.setLogin("Ostap4ik");
        user.setPassword("0q9w8r7t6y");
        user.setAge(12);
        assertThrows(UserMinimumAgeException.class,() -> registrationService.register(user),
                "The user must be at least 18 years of age. Registration is not possible");
    }

    @Test
    void registerWith_negativeUserAge_notOk() {
        User user = new User();
        user.setLogin("Oksana");
        user.setPassword("0q9w8r7t6y");
        user.setAge(-22);
        assertThrows(UserMinimumAgeException.class,() -> registrationService.register(user),
                "You can not set the user a negative age value. Registration is not possible");
    }

    @Test
    void registerWith_nullUserAge_notOk() {
        User user = new User();
        user.setLogin("Oksana");
        user.setPassword("0q9w8r7t6y");
        user.setAge(null);
        assertThrows(UserNullAgeException.class,() -> registrationService.register(user));
    }

    @Test
    void registerWith_containedInDbUser_notOk() {
        User user = new User();
        user.setLogin("Anhelina");
        user.setPassword("0q9w8r7t6y");
        user.setAge(35);
        storageDao.add(user);
        assertThrows(DbContainsSuchUserException.class,() -> registrationService.register(user));
    }

    private Stream<List<User>> userProvider() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("Vasiliy");
        user1.setPassword("012345");
        User user2 = new User();
        user2.setAge(56);
        user2.setLogin("Svitlana");
        user2.setPassword("qwerty");
        User user3 = new User();
        user3.setAge(33);
        user3.setLogin("TarasShev");
        user3.setPassword("asdfghDar");
        User user4 = new User();
        user4.setAge(41);
        user4.setLogin("Larisa");
        user4.setPassword("54321042");
        User user5 = new User();
        user5.setAge(18);
        user5.setLogin("Svyatoslav");
        user5.setPassword("ytrewqgfD");
        User user6 = new User();
        user6.setAge(57);
        user6.setLogin("Zinaida");
        user6.setPassword("gfdsah");
        User user7 = new User();
        user7.setAge(35);
        user7.setLogin("Mykola");
        user7.setPassword("banka_ogirkiv");
        User user8 = new User();
        user8.setAge(29);
        user8.setLogin("Hanna777");
        user8.setPassword("poiuytr");
        User user9 = new User();
        user9.setAge(46);
        user9.setLogin("Vanentyn");
        user9.setPassword("11223344");
        User user10 = new User();
        user10.setAge(23);
        user10.setLogin("Anatoliy");
        user10.setPassword("zxcvbn");
        List<User> users = new ArrayList<>(List.of(
                user1, user2, user3, user4, user5, user6, user7, user8, user9, user10));
        return Stream.of(users);
    }
}
