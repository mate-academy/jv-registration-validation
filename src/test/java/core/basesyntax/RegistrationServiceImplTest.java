package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String DEFAULT_LOGIN = "Alex";
    public static final String DEFAULT_PASSWORD = "1234567890";
    public static final int DEFAULT_AGE = 33;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        NullPointerExceptionDetection(user);
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User(DEFAULT_LOGIN, null, DEFAULT_AGE);
        NullPointerExceptionDetection(user);
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null);
        NullPointerExceptionDetection(user);
    }

    private void NullPointerExceptionDetection(User user) {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(user));
    }

    private void RuntimeExceptionDetection(User user) {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }
}