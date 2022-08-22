package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationServiceException;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void beforeEach() {
        Storage.people.addAll(getDefaultUsers());
    }

    @Test
    void registerUserWithNullData_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void registerUserWithNullLogin_NotOk() {
        User userWithNullLogin = new User(8L, null, "password12345678", 20);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void registerUserWithNegativeAge_NotOk() {
        User userWithNegativeAge = new User(9L, "nineLogin@gmail.com", "ninethPassword", -6);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userWithNegativeAge));
    }

    @Test
    void registerUserWithNullAge_NotOk() {
        User userWithNullAge =
                new User(10L, "tenthLogin@gmail.com", "tenthPassword", null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userWithNullAge));
    }

    @Test
    void registerExistedUser_NotOk() {
        User sameSecondUser =
                new User(2L, "secondLogin@gamil.com", "secondPassword", 30);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(sameSecondUser));
    }

    @Test
    void registerUser_Ok() {
        User user =
                new User(51L, "superUser@gmail.com", "123457", 27);
        User registeredUser = registrationService.register(user);
        assertEquals("superUser@gmail.com", registeredUser.getLogin());
        assertEquals(27, registeredUser.getAge());
        assertEquals("123457", registeredUser.getPassword());
    }

    @Test
    void registerUserWithShortPassword_NotOk() {
        User newUser =
                new User(5L, "fifthLogin@gmail.com", "fifth", 67);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void registerYoungUser_NotOk() {
        User youngUser =
                new User(6L, "sixthLogin@gmail.com", "sixthPassword", 5);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(youngUser));
    }

    @Test
    void registerYoungUserWithShortPassword_NotOk() {
        User youngUserWithShortPassword =
                new User(7L, "seventhLogin@gmail.com", "seven", 10);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(youngUserWithShortPassword));
    }

    @AfterEach
    void afterEach() {
        Storage.people.removeAll(getDefaultUsers());
    }

    private List<User> getDefaultUsers() {
        User firstUser =
                new User(1L, "firstLogin@gamil.com", "firstPassword", 20);
        User secondUser =
                new User(2L, "secondLogin@gamil.com", "secondPassword", 30);
        User thirdUser =
                new User(3L, "thirdLogin@gamil.com", "thirdPassword", 26);
        User fourthUser =
                new User(4L, "fourthLogin@gamil.com", "fourthPassword", 47);
        return List.of(firstUser, secondUser, thirdUser, fourthUser);
    }
}
