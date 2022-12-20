package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void register_valueIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User katrine = new User();
        katrine.setAge(null);
        katrine.setLogin("katrine@gmail.com");
        katrine.setPassword("whatAPity777");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(katrine);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        User katrine = new User();
        katrine.setAge(37);
        katrine.setLogin("katrine@gmail.com");
        katrine.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(katrine);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        User katrine = new User();
        katrine.setAge(37);
        katrine.setLogin(null);
        katrine.setPassword("whatAPity777");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(katrine);
        });
    }

    @Test
    void register_twoMoreValidUsers_Ok() {
        User katty = new User();
        katty.setAge(37);
        katty.setLogin("katty@gmail.com");
        katty.setPassword("whatAPity777");

        User kile = new User();
        kile.setAge(22);
        kile.setLogin("kile@gmail.com");
        kile.setPassword("weirdKon20");

        registrationService.register(katty);
        registrationService.register(kile);

        assertEquals(2, Storage.people.size());
        assertTrue(Storage.people.contains(katty));
        assertTrue(Storage.people.contains(kile));
    }

    @Test
    void register_ageLessThanMinimalAllowedYears_NotOk() {
        User joseph = new User();
        joseph.setAge(16);
        joseph.setLogin("joseph@gmail.com");
        joseph.setPassword("weirdJohny20");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(joseph);
        });
        joseph.setAge(-88);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(joseph);
        });
    }

    @Test
    void register_ageMinimalAllowedYears_Ok() {
        User julie = new User();
        julie.setAge(18);
        julie.setLogin("julie@gmail.com");
        julie.setPassword("whatAPity777");
        registrationService.register(julie);
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(julie));
    }

    @Test
    void register_ageMoreMinimalAllowedYears_Ok() {
        User andrew = new User();
        andrew.setAge(28);
        andrew.setLogin("andrew@gmail.com");
        andrew.setPassword("whatAPity777");
        registrationService.register(andrew);
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(andrew));
    }

    @Test
    void register_passwordLengthLessThanMinimalAllowedSymbols_NotOk() {
        User jack = new User();
        jack.setPassword("11111");
        jack.setAge(28);
        jack.setLogin("jack@gmail.com");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(jack);
        });
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        User john = new User();
        john.setAge(20);
        john.setLogin("john@gmail.com");
        john.setPassword("weirdJohny20");
        registrationService.register(john);

        User newJohn = new User();
        newJohn.setAge(19);
        newJohn.setLogin("john@gmail.com");
        newJohn.setPassword("weirdJohny20");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newJohn);
        });
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }
}
