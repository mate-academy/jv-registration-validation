package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private List<User> users;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(23);
        users.add(user);
    }

    @Test
    void userExistsAndLoginAndPasswordAreValid_Ok() {
        User user = users.get(0);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void userDoesNotExist_NotOk() {
        User user = new User();
        assertFalse(Storage.people.contains(user));
    }

    @Test
    void loginIsLessThanMinimalLength_NotOk() {
        User user = users.get(0);
        user.setLogin("short");
        assertRegistrationException(user, "The length of the login cannot be less "
                + "than the minimal");
    }

    @Test
    void loginIsNull_NotOk() {
        User user = users.get(0);
        user.setLogin(null);
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void loginIsEmpty_NotOk() {
        User user = users.get(0);
        user.setLogin("");
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void passwordIsLessThanMinimalLength_NotOk() {
        User user = users.get(0);
        user.setPassword("short");
        assertRegistrationException(user, "The length of the password cannot be less "
                + "than the minimal");
    }

    @Test
    void passwordIsNull_NotOk() {
        User user = users.get(0);
        user.setPassword(null);
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void passwordIsEmpty_NotOk() {
        User user = users.get(0);
        user.setPassword("");
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void ageIsLessThanMinimal_NotOk() {
        User user = users.get(0);
        user.setAge(17);
        assertRegistrationException(user, "The user's age is less "
                + "than the minimal age allowed for registration");
    }

    private void assertRegistrationException(User user, String errorMessage) {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), errorMessage);
    }
}
