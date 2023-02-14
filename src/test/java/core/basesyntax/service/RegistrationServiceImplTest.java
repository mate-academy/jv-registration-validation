package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @Test
    void register_nullAge_notOk() {
        User user = new User(1L,"Bob","12345",0);
        assertThrows(ExpectedException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(1L,null,"1234567",20);
        assertThrows(ExpectedException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectLogin_notOk() {
        User user = new User(1L,"Ivan ivanov","1234567",21);
        Storage.people.add(user);
        User userFromRegister = new User(1L,"Ivan ivanov","1234567",21);
        assertThrows(ExpectedException.class, () -> registrationService.register(userFromRegister));
    }

    @Test
    void register_IncorrectPassword_notOk() {
        User user = new User(1L,"Jane",null,22);
        assertThrows(ExpectedException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessAge_notOk() {
        User user = new User(1L,"Nike","12345",15);
        assertThrows(ExpectedException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validData() {
        int size = Storage.people.size();
        User user = new User(1L,"Nike","1234567",22);
        registrationService.register(user);
        assertEquals(size + 1,Storage.people.size());
    }
}
