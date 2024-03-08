package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDao() {
            @Override
            public User add(User user) {
                return user;
            }

            @Override
            public User get(String login) {

                if (login.equals("existingUser")) {
                    return new User("existingUser", "password123", 30);
                }
                return null;
            }
        };
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void testRegisterValidUser() {
        User user = new User("testUser", "password123", 25);

        try {
            User registeredUser = registrationService.register(user);
            assertNotNull(registeredUser);
            assertEquals(user, registeredUser);
        } catch (RegistrationException e) {
            fail("Unexpected RegistrationException: " + e.getMessage());
        }
    }

    @Test
    void testRegisterExistingUser() {
        User existingUser = new User("existingUser", "password123", 30);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(existingUser));

        assertEquals("User with this login already exists", exception.getMessage());
    }

    @Test
    void testRegisterInvalidUser() {
        User invalidUser = new User("test", "pass", 20);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(invalidUser));

        assertEquals("Login and password must "
                + "contain at least 6 characters", exception.getMessage());
    }
}
