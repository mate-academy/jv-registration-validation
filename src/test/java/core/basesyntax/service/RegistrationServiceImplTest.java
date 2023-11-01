package core.basesyntax.service;

import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final User DEFAULT_VALID_USER = new User();

    private static RegistrationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        DEFAULT_VALID_USER.setAge(20);
        DEFAULT_VALID_USER.setPassword("DefaultPassword");
        DEFAULT_VALID_USER.setLogin("DefaultLogin");
    }

    @Test
    void register_nullAge_notOk() {
        DEFAULT_VALID_USER.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void name1() {
    }

    @Test
    void name2() {
    }

    @Test
    void name3() {
    }

    @Test
    void name4() {
    }

    @Test
    void name5() {
    }
}