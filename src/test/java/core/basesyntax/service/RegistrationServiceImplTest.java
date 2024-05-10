package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final List<User> correctData = new ArrayList<>();
    private static final List<User> notValidData = new ArrayList<>();
    private static final int ONE_USER = 1;
    private static final int FIRST_USER = 0;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User testUser;

    @BeforeAll
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
        initializeCorrectDataUsers();
        setNotValidData();
    }

    @BeforeEach
    void beforeEach() {
        testUser = new User();
    }

    @Test
    void registerOneUser_OK() {
        registrationService.register(correctData.get(FIRST_USER));
        assertEquals(ONE_USER, Storage.people.size(),
                "Storage should contains one user");
        assertEquals(correctData.get(FIRST_USER),
                storageDao.get(correctData.get(FIRST_USER).getLogin()));
    }

    @Test
    void registerUser_OK() {
        for (User correctDatum : correctData) {
            registrationService.register(correctDatum);
        }
        assertEquals(correctData.size(), Storage.people.size(),
                "Storage should contains " + correctData.size() + " user`s");
    }

    @Test
    void passwordIsNull_notOK() {
        testUser.setLogin("qw3wlfoaad");
        testUser.setPassword(null);
        testUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void loginIsNull_NotOk() {
        testUser.setLogin(null);
        testUser.setPassword("12334566332");
        testUser.setAge(23);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void passwordAndLoginIsNull_notOK() {
        testUser.setLogin(null);
        testUser.setPassword(null);
        testUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void registerNullAge_notOk() {
        testUser.setLogin("qw3qweq");
        testUser.setPassword("12334566");
        testUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void notValidData_NotOK() {
        assertThrows(InvalidDataException.class, () -> {
            for (User notValidDatum : notValidData) {
                registrationService.register(notValidDatum);
            }
        });
    }

    @Test
    void passwordValidLoginNotValid_notOK() {
        testUser.setLogin("qw3");
        testUser.setPassword("12334566");
        testUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void passwordNotValidLoginValid_notOK() {
        testUser.setLogin("ValidLogin");
        testUser.setPassword("123");
        testUser.setAge(21);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void userAgeMoreThanEighteen() {
        for (int i = 0; i < correctData.size(); i++) {
            registrationService.register(correctData.get(i));
            int expectedSize = Storage.people.size();
            assertEquals(expectedSize, i + 1, "Storage should contains " + i + 1 + " users");
        }
        testUser.setLogin("1qweqweww");
        testUser.setPassword("123213123");
        testUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void userNull_NotOK() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void sameUserLoginInTheStorage_NotOK() {
        for (User user : correctData) {
            assertDoesNotThrow(() -> registrationService.register(user));
        }
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(correctData.get(FIRST_USER));
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private static void initializeCorrectDataUsers() {
        User bob = new User();
        bob.setLogin("login1");
        bob.setPassword("123456");
        bob.setAge(50);
        correctData.add(bob);
        User alice = new User();
        alice.setLogin("login123");
        alice.setPassword("1234567");
        alice.setAge(34);
        correctData.add(alice);
        User john = new User();
        john.setLogin("login12345");
        john.setPassword("1234567890");
        john.setAge(22);
        correctData.add(john);
        User jack = new User();
        jack.setLogin("login123456");
        jack.setPassword("12345678");
        jack.setAge(18);
        correctData.add(jack);
        User mike = new User();
        mike.setLogin("login12345678");
        mike.setPassword("1234567899");
        mike.setAge(29);
        correctData.add(mike);
    }

    private static void setNotValidData() {
        User first = new User();
        first.setLogin("asdf");
        first.setPassword("1312");
        first.setAge(12);
        notValidData.add(first);
        User second = new User();
        second.setLogin("df");
        second.setPassword("13");
        second.setAge(8);
        notValidData.add(second);
        User third = new User();
        third.setLogin("dfqwe");
        third.setPassword("13234");
        third.setAge(17);
        notValidData.add(third);
        User fourth = new User();
        fourth.setLogin("q");
        fourth.setPassword("1");
        fourth.setAge(1);
        notValidData.add(fourth);
    }
}
