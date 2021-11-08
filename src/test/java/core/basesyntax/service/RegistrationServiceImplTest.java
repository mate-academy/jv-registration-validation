package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

        private static final User validUser = new User("Ivanovich", "3455667",18);
        private static final User twoValidUser = new User("Sidorovich", "34jkl667",33);

        private static final User nullUser = new User(null, null,null);
        private static final User nullLoginUser = new User(null, "dgfhjjgh",45);
        private static final User nullPasswordUser = new User("Petrov", null,21);
        private static final User nullAgeUser = new User("Petrovko", "121212",null);

        private static final User ageUser_NotOk = new User("Petrovich", "1211111",10);
        private static final User negativeAgeUser_NotOk = new User("Petrovichev", "121ll11",-100);
        private static final User passwordUser_NotOk = new User("Petro", "1211w",77);

        private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

        @Test
        public void registered_sameUser_NotOk() {
            registrationService.register(validUser);
            registrationService.register(twoValidUser);
            assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
        }

        @Test
        public void registered_Ok() {
            User actual = registrationService.register(validUser);
            assertEquals(validUser, actual);
        }

        @Test
        public void registered_nullUser_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(nullUser));
        }

        @Test
        public void registered_nullLogin_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(nullLoginUser));
        }

        @Test
        public void registered_nullPassword_NotOk () {
            assertThrows(RuntimeException.class, () -> registrationService.register(nullPasswordUser));
        }

        @Test
        public void registered_nullAge_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(nullAgeUser));
        }

        @Test
        public void registered_age_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(ageUser_NotOk));
        }

        @Test
        public void registered_negative_age_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(negativeAgeUser_NotOk));
        }

        @Test
        public void registered_password_NotOk() {
            assertThrows(RuntimeException.class, () -> registrationService.register(passwordUser_NotOk));
        }

        @AfterEach
        void tearDown() {
            Storage.people.clear();
        }
    }
