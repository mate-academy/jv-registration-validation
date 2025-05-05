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
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_inputUserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLoginUser_NotOk() {
        User nullLoginUser = new User(null, "davidbekham", 23);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        User nullPasswordUser = new User("Anna", null, 30);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_nullAgeUser_NotOk() {
        User nullAgeUser = new User("Greg", "greg342", null);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullAgeUser));
    }

    @Test
    void register_validUser_Ok() {
        User bogdan = new User("Bogdan", "bogdanchik", 19);
        assertEquals(bogdan, registrationService.register(bogdan));
    }

    @Test
    void register_userIsAlreadyExist_NotOk() {
        User bob = new User("Bob", "bobikbobik", 20);
        registrationService.register(bob);
        assertThrows(RuntimeException.class, () -> registrationService.register(bob));
    }

    @Test
    void register_blankLoginUser_NotOk() {
        User blankLoginUser = new User("    ", "demon228", 27);
        assertThrows(RuntimeException.class, () -> registrationService.register(blankLoginUser));
    }

    @Test
    void register_emptyLoginUser_NotOk() {
        User emptyLoginUser = new User("", "demon228", 27);
        assertThrows(RuntimeException.class, () -> registrationService.register(emptyLoginUser));
    }

    @Test
    void register_theSameLoginUser_NotOk() {
        User andriy = new User("Andriy", "validpassword", 25);
        assertEquals(andriy, registrationService.register(andriy));
        User secondAndriy = new User("Andriy", "andreyko", 19);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondAndriy));
    }

    @Test
    void register_passwordLengthLessThanSix_NotOk() {
        User john = new User("John", "hello", 35);
        assertThrows(RuntimeException.class, () -> registrationService.register(john));
    }

    @Test
    void register_emptyPasswordUser() {
        User jeremy = new User("Jeremy", "", 23);
        assertThrows(RuntimeException.class, () -> registrationService.register(jeremy));
    }

    @Test
    void register_blankPasswordUser() {
        User lucia = new User("Lucia", "       ", 27);
        assertThrows(RuntimeException.class, () -> registrationService.register(lucia));
    }

    @Test
    void register_userAgeIsLessThanEighteen_NotOk() {
        User alice = new User("Alice", "alice321", 17);
        assertThrows(RuntimeException.class, () -> registrationService.register(alice));
    }

    @Test
    void register_negativeAgeUser_NotOk() {
        User kevin = new User("Kevin", "kevinz12", -23);
        assertThrows(RuntimeException.class, () -> registrationService.register(kevin));
    }

    @Test
    void register_userAgeIsEighteen() {
        User bony = new User("Bony", "bonypony", 18);
        assertEquals(bony, registrationService.register(bony));
    }
}
