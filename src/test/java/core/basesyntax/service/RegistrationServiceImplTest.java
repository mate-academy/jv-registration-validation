package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.DuplicateAnExistingLoginException;
import core.basesyntax.exceptions.NotCorrectAgeException;
import core.basesyntax.exceptions.NotCorrectPassword;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void beforeEach() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User kirill = new User();
        kirill.setAge(18);
        kirill.setLogin("Anna@gmaol.com");
        kirill.setPassword("123456789");
        User bob = new User();
        bob.setAge(18);
        bob.setLogin("Bob@gmaol.com");
        bob.setPassword("123456789");
        User actualBob = registrationService.register(bob);
        assertEquals(bob,actualBob);
        User actualKirill = registrationService.register(kirill);
        assertEquals(kirill,actualKirill);
    }

    @Test
    void register_nullPassword_NotOk() {
        User nullPasswordUser = new User();
        nullPasswordUser.setPassword(null);
        nullPasswordUser.setLogin("nullPasswordUser@gmail.com");
        nullPasswordUser.setAge(22);
        assertThrows(NullPointerException.class,()
                -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        User nullLogin = new User();
        nullLogin.setPassword("123456789");
        nullLogin.setLogin(null);
        nullLogin.setAge(22);
        assertThrows(NullPointerException.class,()
                -> registrationService.register(nullLogin));
    }

    @Test
    void register_ageLessThanMin_NotOk() {
        User ungAndgrey = new User();
        ungAndgrey.setAge(15);
        ungAndgrey.setLogin("Andrey@gmial.com");
        ungAndgrey.setPassword("123456789");
        assertThrows(NotCorrectAgeException.class,()
                -> registrationService.register(ungAndgrey));
    }

    @Test
    void register_notValidPassword_NotOk() {
        User vadim = new User();
        vadim.setPassword("12345");
        vadim.setAge(22);
        vadim.setLogin("Vadim@gmail.com");
        assertThrows(NotCorrectPassword.class,()
                -> registrationService.register(vadim));
    }

    @Test
    void register_duplicateAnExistingLogin_NotOk() {
        User anna = new User();
        anna.setAge(18);
        anna.setLogin("Anna@gmaol.com");
        anna.setPassword("123456789");
        User anotherAnna = new User();
        anotherAnna.setAge(18);
        anotherAnna.setLogin("Anna@gmaol.com");
        anotherAnna.setPassword("123456789");
        registrationService.register(anna);
        assertThrows(DuplicateAnExistingLoginException.class,()
                -> registrationService.register(anotherAnna));
    }

    @Test
    void register_registerMethodValidCheck_Ok(){
        User pavel = new User();
        User anton = new User();
        User david = new User();
        pavel.setLogin("pavel@gmail.com");
        pavel.setPassword("123456789");
        pavel.setAge(19);
        anton.setLogin("anton@gmail.com");
        anton.setPassword("123456789");
        anton.setAge(25);
        david.setLogin("david@gmail.com");
        david.setPassword("123456789");
        david.setAge(35);
        registrationService.register(pavel);
        registrationService.register(anton);
        registrationService.register(david);
        int actualStorageSize = Storage.people.size();
        assertEquals(3,actualStorageSize);
        User actualFirstStorageVisitor = Storage.people.get(0);
        assertEquals(pavel,actualFirstStorageVisitor);
        User actualSecondStorageVisitor = Storage.people.get(1);
        assertEquals(anton,actualSecondStorageVisitor);
        User actualThirdStorageVisitor = Storage.people.get(2);
        assertEquals(david,actualThirdStorageVisitor);
    }
}
