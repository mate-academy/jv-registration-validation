package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    private User userOne;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }


    @BeforeEach
    void setUp() {
        userOne = new User();
        userOne.setAge(33);
        userOne.setLogin("qwerty");
        userOne.setPassword("1r3T41e4");
    }

    @AfterEach
    void setUP() {
        Storage.people.clear();
    }


    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null),
                "Verifying is failed. User can't be null.");
    }

    @Test
    void register_passwordIsNull_notOk() {
        userOne.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userOne),
                "Password can't be null.");
    }

    @Test
    void register_passwordLengthIsNormal_ok() {
        User userReturned = registrationService.register(userOne);
        assertEquals(userOne, userReturned,
                "Verifying is failed. Password " + userOne.getPassword()
                        + " with length " + userOne.getPassword().length() + "is ok.");
    }

    @Test
    void register_passwordLengthIsShort_notOk() {
        userOne.setPassword("1w2Dr");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userOne),
                "Verifying is failed. Too shot password.");
    }

    @Test
    void register_ageIsNull_notOk() {
        userOne.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userOne),
                "Age can't be null.");
    }

    @Test
    void register_ageIsCorrect_ok() {
        User userReturned = registrationService.register(userOne);
        assertEquals(userOne, userReturned,
                "Verifying is failed. User aged " + userOne.getAge() + "can be added.");
    }

    @Test
    void register_underAge_notOk() {
        userOne.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userOne),
                "Verifying is failed. User aged " + userOne.getAge() + " is too young.");
    }

    @Test
    void register_userNotInStorage_ok() {
        assertEquals(userOne, registrationService.register(userOne),
                "Verifying is failed. User can be added. No user with nickname "
                        + userOne.getLogin() + " in the storage.");
    }

    @Test
    void register_userIsInStorage_notOk() {
        registrationService.register(userOne);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userOne),
                "Verifying is failed. There is a user with this username " + userOne);
    }
}
