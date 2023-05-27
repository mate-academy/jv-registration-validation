package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void theAgeIsLowerThanEighteen_NotOk() {
        User oleh = new User("OlezhaNotToxic", "KuzanBamBam", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(oleh));
    }

    @Test
    void theAgeIsEighteen_Ok() {
        User sashka = new User("KlymKlym", "loveToPlayWoT", 18);
        assertEquals(18, sashka.getAge());
        assertDoesNotThrow(() -> registrationService.register(sashka));
    }

    @Test
    void validUser_Ok() {
        User misha = new User("Terminator1337", "funtik9099", 20);
        assertDoesNotThrow(() -> registrationService.register(misha));
    }

    @Test
    void passwordLengthEqualsSix_Ok() {
        User maria = new User("MashkaLubidKashku", "tt55yy", 33);
        assertEquals(6, maria.getPassword().length());
        assertDoesNotThrow(() -> registrationService.register(maria));
    }

    @Test
    void passwordLengthEqualsZero_NotOk() {
        User oleksandr = new User("Koval's'kii", "", 30);
        assertEquals(0, oleksandr.getPassword().length());
        assertThrows(RegistrationException.class, () -> registrationService.register(oleksandr));
    }

    @Test
    void loginLengthEqualsSix_Ok() {
        User bohdan = new User("Bohdan", "KrutoiChupicka422", 30);
        assertEquals(6, bohdan.getLogin().length());
        assertDoesNotThrow(() -> registrationService.register(bohdan));
    }

    @Test
    void loginLengthEqualsZero_NotOk() {
        User incognito = new User("", "loveToEatCereal", 32);
        assertEquals(0, incognito.getLogin().length());
        assertThrows(RegistrationException.class, () -> registrationService.register(incognito));
    }

    @Test
    void userNotUnique_NotOk() {
        User dima = new User("DimaKrutoi", "qwerty2000", 19);
        Storage.people.add(dima);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(new User("DimaKrutoi", "qwerty202", 23)));
    }

    @Test
    void loginEqualsNull_NotOk() {
        User dasha = new User(null, "ILoveMyMother", 25);
        assertNull(dasha.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(dasha));
    }

    @Test
    void passwordEqualsNull_NotOk() {
        User georgii = new User("BogBoy422", null, 33);
        assertNull(georgii.getPassword());
        assertThrows(RegistrationException.class, () -> registrationService.register(georgii));
    }

    @Test
    void ageEqualsNull_NotOk() {
        User andrii = new User("AndriiDorosliy", "sobaka1", null);
        assertNull(andrii.getAge());
        assertThrows(RegistrationException.class, () -> registrationService.register(andrii));
    }

    @Test
    void userEqualsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }
}
