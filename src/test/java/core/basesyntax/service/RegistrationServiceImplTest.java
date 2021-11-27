package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @Test
    void noRepeatedUserLogin() {
        registrationService.register(new User(0L,"goodLogin_A","myPassword_A",56));
        registrationService.register(new User(0L,"goodLogin_B","myPassword_A",56));
        registrationService.register(new User(0L,"goodLogin_A","myPassword_B",57));
        assertTrue(Storage.people.contains(new User(0L,"goodLogin_A","myPassword_A",56)));
        assertTrue(Storage.people.contains(new User(0L,"goodLogin_B","myPassword_A",56)));
        assertFalse(Storage.people.contains(new User(0L,"goodLogin_A","myPassword_B",57)));
        System.out.println(Storage.people.size());
    }

    @Test
    void userAgeHaveToMoreOrEqual18() {
        registrationService.register(new User(0L,"goodLogin_C","myPassword_A",18));
        registrationService.register(new User(0L,"goodLogin_D","myPassword_A",17));
        assertTrue(Storage.people.contains(new User(0L,"goodLogin_C","myPassword_A",18)));
        assertFalse(Storage.people.contains(new User(0L,"goodLogin_D","myPassword_A",17)));

    }

    @Test
    void userPasswordHaveToAtLeast6Charecters() {
        registrationService.register(new User(0L,"goodLoginF","myPas",18));
        assertFalse(Storage.people.contains(new User(0L,"goodLoginF","myPas",18)));
    }
}
