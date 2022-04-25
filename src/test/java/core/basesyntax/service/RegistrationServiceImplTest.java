package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_uniqueLogin_Ok() {
        User expected = new User("Bastard", "123456", 20);
        User expected1 = new User("Bitch", "123456", 24);
        registrationService.register(expected);
        registrationService.register(expected1);
        assertEquals(expected, Storage.people.get(0));
        assertEquals(expected1, Storage.people.get(1));
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_nullLogin_NotOk() {
        User userNullLog = new User(null, "123456", 20);
        assertThrows(RuntimeException.class, () -> registrationService.register(userNullLog));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_sameLogin_NotOk() {
        User user = new User("Moron", "1234567", 19);
        User sameLoginUser = new User("Moron", "123456", 21);
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLoginUser));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_underOrNullAge_NotOk() {
        User userNullAge = new User("Dark_Vlastelin", "123456", null);
        User underAgeUser = new User("Moron", "1234567", 17);
        assertThrows(RuntimeException.class, () -> registrationService.register(underAgeUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(userNullAge));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_negativeAge_NotOk() {
        User negativeAgeUser = new User("Gnom", "543231",-3);
        assertThrows(RuntimeException.class, () -> registrationService.register(negativeAgeUser));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_ShortOrNullPassword_NotOk() {
        User wrongPassUser = new User("Dumb", "12345", 25);
        User userNullPass = new User("Black", null, 15);
        assertThrows(RuntimeException.class, () -> registrationService.register(wrongPassUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(userNullPass));
        assertEquals(0, Storage.people.size());
    }
}
