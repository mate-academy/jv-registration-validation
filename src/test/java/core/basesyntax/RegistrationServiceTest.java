package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {

    @Test
    public void testLogin_WithValidLogin_Ok() {
        User user = new User("kathy76", "653287", 19);
        User user1 = new User("toto5678", "prit12345", 23);
        User user2 = new User("potom123", "utiny5467", 45);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void testLogin_WithTooShortLogin_NotOk() {
        User user = new User("asdfg", "poi5437", 64);
        User user1 = new User("kutam76", "1234tyr", 42);
        User user2 = new User("tyre3241", "9843oitr", 24);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testLogin_WithNullLogin_NotOk() {
        User user = new User(null, "098756hg", 54);
        User user1 = new User("jhere123", "74568erw", 33);
        User user2 = new User("troi4576", "984321ed", 31);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testLogin_WithBlankLogin_NotOk() {
        User user = new User("", "ptyre123", 20);
        User user1 = new User("rtjww23", "rewku234", 44);
        User user2 = new User("tyrex45", "12384fhg", 33);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testLogin_IfUserWithGivenLoginExists_NotOk() {
        User user = new User("qwerty23", "poty3456", 21);
        User user1 = new User("qwerty23", "portugal1283", 45);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testPassword_WithValidPassword_Ok() {
        User user = new User("tremor43", "12289fg", 47);
        User user1 = new User("jkgfsieru43", "85723hgf", 50);
        User user2 = new User("yuerjk457", "hfguri4758", 54);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void testPassword_WithTooShortPassword_NotOk() {
        User user = new User("tynol234", "123e", 32);
        User user1 = new User("potrw234", "47563kjf", 75);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testPassword_IfPasswordIsBlank_NotOk() {
        User user = new User("iutryeu21", "", 44);
        User user1 = new User("jkgfhh34", "otr485739", 32);
        User user2 = new User("jgkfj7564", "37465hfg", 55);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testPassword_IfPasswordIsNull_NotOk() {
        User user = new User("ytruey34", null, 25);
        User user1 = new User("oitru48567", "34859tjgh", 44);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testAge_WithValidAge_Ok() {
        User user = new User("urty341", "34859381", 34);
        User user1 = new User("irkthjw34", "djfhg4321", 67);
        User user2 = new User("kfjgh4567", "fhgj34568", 47);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void testAge_WithLessThanEighteen_NotOk() {
        User user = new User("jgfhsur4536", "3475jgfh", 12);
        User user1 = new User("vbcnsfj433", "321fhrhe", 32);
        User user2 = new User("fhgjh123", "dhfg1234", 34);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void testAge_WithNullAge_NotOk() {
        User user = new User("poidu342", "123475htygf", null);
        User user1 = new User("pofury234", "283746rythg", 43);
        User user2 = new User("hfdg4657", "iery457632", 56);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user1);
        registrationService.register(user2);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}
