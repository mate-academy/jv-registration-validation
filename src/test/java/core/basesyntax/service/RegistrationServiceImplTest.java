package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "Login";
    private static final int DEFAULT_AGE = 18;
    private User user;
    private RegistrationService service;

    @BeforeEach
    void beforeAll() {
        service = new RegistrationServiceImpl();
        user = new User();
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
    }

    @Test
    void passwordIsNotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                service.register(user)
        );
    }

    @Test
    void userIsOk() {
        User actual = service.register(user);
        assertTrue(actual == user);
    }

    @Test
    void ageIsNotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                service.register(user)
        );
    }

    @Test
    void userIsNull() {
        assertThrows(RuntimeException.class, () ->
                service.register(null)
        );
    }

    @Test
    void addSameUserNotOk() {
        service.register(user);
        assertThrows(RuntimeException.class, () ->
                service.register(user)
        );
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
