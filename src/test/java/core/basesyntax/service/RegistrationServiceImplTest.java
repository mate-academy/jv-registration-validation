package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User validUser = new User();
        validUser.setAge(20);
        validUser.setLogin("validUser123");
        validUser.setPassword("strongPassword");
        service.register(validUser);
        assertEquals(1, Storage.people.size());
        User storedUser = Storage.people.get(0);
        assertEquals(validUser.getLogin(), storedUser.getLogin());
        assertEquals(validUser.getPassword(), storedUser.getPassword());
        assertEquals(validUser.getAge(), storedUser.getAge());
    }

    @Test
    void register_shortPass_NotOk() {
        User shortPassUser = new User();
        shortPassUser.setAge(22);
        shortPassUser.setLogin("validLogin23");
        shortPassUser.setPassword("pass");
        assertThrows(InvalidUserExeption.class, () -> service.register(shortPassUser));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_shortLogin_NotOk() {
        User shortLoginUser = new User();
        shortLoginUser.setAge(19);
        shortLoginUser.setPassword("normalPass");
        shortLoginUser.setLogin("login");
        assertThrows(InvalidUserExeption.class, () -> service.register(shortLoginUser));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_invalidAge_NotOk() {
        User invalidUserAge = new User();
        invalidUserAge.setAge(17);
        invalidUserAge.setLogin("ValidLogin123");
        invalidUserAge.setPassword("normalPass333");
        assertThrows(InvalidUserExeption.class, () -> service.register(invalidUserAge));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_existingLogin_NotOk() {
        User existingUser = new User();
        existingUser.setPassword("normalPass");
        existingUser.setAge(25);
        existingUser.setLogin("validLogin");
        Storage.people.add(existingUser);
        User anotherUser = new User();
        anotherUser.setLogin("validLogin");
        anotherUser.setAge(18);
        anotherUser.setPassword("goodPassword123");
        assertThrows(InvalidUserExeption.class, () -> service.register(anotherUser));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidUserExeption.class, () -> service.register(null));
        assertEquals(0, Storage.people.size());
    }
}
