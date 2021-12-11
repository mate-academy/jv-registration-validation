package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @Test
    void registrationService_noRepeatedUserLogin_Ok() {
        registrationService.register(new User(0L,"goodLogin_A","myPassword_A",56));
        registrationService.register(new User(0L,"goodLogin_B","myPassword_A",56));
        assertTrue(Storage.people.contains(new User(0L,"goodLogin_A","myPassword_A",56)));
        assertTrue(Storage.people.contains(new User(0L,"goodLogin_B","myPassword_A",56)));
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"goodLogin_A","myPassword_B",57)));
    }

    @Test
    void registrationService_userAgeHaveToMoreOrEqual18ButNoMore122_notOk() {
        registrationService.register(new User(0L,"goodLogin_C","myPassword_A",18));
        assertTrue(Storage.people.contains(new User(0L,"goodLogin_C","myPassword_A",18)));
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"goodLogin_D","myPassword_A",17)));
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"goodLogin_D","myPassword_A",123)));
    }

    @Test
    void registrationService_nullLogin_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,null,"myPassword_A",18)));
    }

    @Test
    void registrationService_NullPassword_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"goodLogin_C",null,18)));
    }

    @Test
    void registrationService_NullAge_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"goodLogin_C","myPassword_A",null)));
    }

    @Test
    void registrationService_userPasswordHaveToAtLeast6Charecters_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"goodLoginF","myPas",18)));
    }

    @Test
    void registrationService_loginEmptySymbol_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"badLoginF ","myPas",18)));
    }

    @Test
    void registrationService_passwordEmptySymbol_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(0L,"goodLoginF","my badPas",18)));
    }
}
