package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserTest {
    private static final String VALID_LOGIN = "testuser";
    private static final String VALID_PASSWORD = "password123";
    private static final int VALID_AGE = 25;
    private static final Long VALID_ID = 1L;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Nested
    class PropertyTests {
        @Test
        void setId_validId_ok() {
            user.setId(VALID_ID);
            assertEquals(VALID_ID, user.getId());
        }

        @Test
        void setLogin_validLogin_ok() {
            user.setLogin(VALID_LOGIN);
            assertEquals(VALID_LOGIN, user.getLogin());
        }

        @Test
        void setPassword_validPassword_ok() {
            user.setPassword(VALID_PASSWORD);
            assertEquals(VALID_PASSWORD, user.getPassword());
        }

        @Test
        void setAge_validAge_ok() {
            user.setAge(VALID_AGE);
            assertEquals(VALID_AGE, user.getAge());
        }
    }

    @Nested
    @Disabled("Validation not implemented in User class yet")
    class ValidationTests {
        private static final String SHORT_LOGIN = "test";
        private static final String SHORT_PASSWORD = "pass";
        private static final int UNDERAGE = 17;

        @Test
        void setLogin_shortLogin_notOk() {
            assertThrows(InvalidDataException.class,
                    () -> user.setLogin(SHORT_LOGIN));
        }

        @Test
        void setPassword_shortPassword_notOk() {
            assertThrows(InvalidDataException.class,
                    () -> user.setPassword(SHORT_PASSWORD));
        }

        @Test
        void setAge_underageUser_notOk() {
            assertThrows(InvalidDataException.class,
                    () -> user.setAge(UNDERAGE));
        }

        @Test
        void setLogin_duplicateLogin_notOk() {
            User existingUser = new User();
            existingUser.setLogin(VALID_LOGIN);
            assertThrows(InvalidDataException.class,
                    () -> user.setLogin(VALID_LOGIN));
        }
    }
}
