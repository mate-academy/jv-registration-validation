package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private static final User VALID_USER = new User("Ivanovich", "3455667",18);
        private static final User TWO_VALID_USER = new User("Sidorovich", "34jkl667",33);
        private static final User SAME_LOGIN_VALID_USER = new User("Ivanovich", "pp477777",85);

        private static final User NULL_USER = new User(null, null,null);
        private static final User NULL_LOGIN_USER = new User(null, "dgfhjjgh",45);
        private static final User NULL_PASSWORD_USER = new User("Petrov", null,21);
        private static final User NULL_AGE_USER = new User("Petrovko", "121212",null);

        private static final User AGE_USER_NOT_OK = new User("Petrovich", "1211111",10);
        private static final User NEGATIVE_AGE_USER_NOT_OK = new User("Petrovichev", "121ll11",-100);
        private static final User PASSWORD_USER_NOT_OK = new User("Petro", "1211w",77);

        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();


        @Test
        public void registered_sameUser_NotOk() {
            registrationService.register(VALID_USER);
            registrationService.register(TWO_VALID_USER);
            assertThrows(RuntimeException.class, () -> registrationService.register(SAME_LOGIN_VALID_USER));
        }

        @Test
        public void registered_Ok() {

            User actual = registrationService.register(VALID_USER);
            assertEquals(VALID_USER, actual);
        }

        @Test
        public void registered_nullUser_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(NULL_USER));
        }

        @Test
        public void registered_nullLogin_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(NULL_LOGIN_USER));
        }

        @Test
        public void registered_nullPassword_NotOk () {
            assertThrows(RuntimeException.class, () -> registrationService.register(NULL_PASSWORD_USER));
        }

        @Test
        public void registered_nullAge_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(NULL_AGE_USER));
        }

        @Test
        public void registered_age_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(AGE_USER_NOT_OK));
        }

        @Test
        public void registered_negative_age_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(NEGATIVE_AGE_USER_NOT_OK));
        }

        @Test
        public void registered_password_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(PASSWORD_USER_NOT_OK));

        }

        @AfterEach
        void tearDown() {
            Storage.people.clear();
        }
    }
