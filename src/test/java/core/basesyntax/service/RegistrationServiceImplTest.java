package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
        RegistrationService registrationService;
        User user;

        @BeforeEach
        void setUp() {
                user = new User();
                user.setId(111L);
                user.setLogin("reexxx");
                user.setPassword("qwerty");
                user.setAge(25);
                registrationService = new RegistrationServiceImpl();
        }

        @AfterEach
        void tearDown() {
                Storage.people.clear();
        }

        @Test
        void register_ValidUser_Ok() {
                User actual = registrationService.register(user);
                assertEquals(actual, user);
        }

        @Test
        void register_UserWithShortLogin_NotOk() {
                user.setLogin("reex");
                assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        }

        @Test
        void register_UserWithNullLogin_NotOk() {
                user.setLogin(null);
                assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        }

        @Test
        void register_UserWithShortPassword_NotOk() {
                user.setPassword("qwert");
                assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        }

        @Test
        void register_UserWithNullPassword_NotOk() {
                user.setPassword(null);
                assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        }

        @Test
        void register_UserWithInvalidAge_NotOk() {
                user.setAge(17);
                assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        }

        @Test
        void register_UserWithNullAge_NotOk() {
                user.setAge(null);
                assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        }

        @Test
        void register_AlreadyRegisteredUser_NotOk() {
                registrationService.register(user);
                assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        }
}

























