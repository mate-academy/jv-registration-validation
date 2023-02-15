package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void beforeEach() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(1L,"Bob","12345",0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_checkLogin_notOk() {
        User user = new User(1L,null,"1234567",20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectLogin_notOk() {
        User user = new User(1L,"Ivan ivanov","1234567",21);
        Storage.people.add(user);
        User userReg = new User(1L,"Ivan ivanov","1234567",21);
        assertThrows(RegistrationException.class, () -> registrationService.register(userReg));
    }

    @Test
    void register_IncorrectPassword_notOk() {
        User user = new User(1L,"Jane","12345",22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessAge_notOk() {
        User user = new User(1L,"Nike","12345",15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validData() {
        int size = Storage.people.size();
        User user = new User(1L,"Nike","1234567",22);
        registrationService.register(user);
        assertEquals(size + 1,Storage.people.size());
    }

    @AfterEach
    public void afterEach() {
        Storage.people.clear();
    }
}
