package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MIN_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        String expectedMessage = "User can't be null";
        RegistrationException exception = assertThrows(RegistrationException.class, 
                () -> service.register(null));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLoginUser = new User();
        nullLoginUser.setPassword("testpassword");
        nullLoginUser.setAge(19);
        String expectedMessage = "User's login can't be empty";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(nullLoginUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = new User();
        nullPasswordUser.setLogin("dianazotsenko");
        nullPasswordUser.setAge(19);
        String expectedMessage = "User's password can't be empty";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(nullPasswordUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        User nullAgeUser = new User();
        nullAgeUser.setLogin("bobforester");
        nullAgeUser.setPassword("testpassword");
        String expectedMessage = "User's age can't be empty";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(nullAgeUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_emptyLogin_notOk() {
        User emptyLoginUser = new User();
        emptyLoginUser.setLogin("");
        emptyLoginUser.setPassword("testpassword");
        emptyLoginUser.setAge(19);
        String expectedMessage = "Login must be at least " + MIN_LENGTH + " characters long.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(emptyLoginUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_threeCharacterLogin_notOk() {
        User threeCharacterLoginUser = new User();
        threeCharacterLoginUser.setLogin("dia");
        threeCharacterLoginUser.setPassword("testpassword");
        threeCharacterLoginUser.setAge(19);
        String expectedMessage = "Login must be at least " + MIN_LENGTH + " characters long.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(threeCharacterLoginUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_fiveCharacterLogin_notOk() {
        User fivecharacterLoginUser = new User();
        fivecharacterLoginUser.setLogin("diana");
        fivecharacterLoginUser.setPassword("testpassword");
        fivecharacterLoginUser.setAge(19);
        String expectedMessage = "Login must be at least " + MIN_LENGTH + " characters long.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(fivecharacterLoginUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_sixCharacterLogin_ok() {
        User sixCharacterLoginUser = new User();
        sixCharacterLoginUser.setLogin("dianaz");
        sixCharacterLoginUser.setPassword("testpassword");
        sixCharacterLoginUser.setAge(19);
        assertEquals(sixCharacterLoginUser, service.register(sixCharacterLoginUser));
    }

    @Test
    void register_eightCharacterLogin_ok() {
        User eightCharacterLoginUser = new User();
        eightCharacterLoginUser.setLogin("dianazot");
        eightCharacterLoginUser.setPassword("testpassword");
        eightCharacterLoginUser.setAge(19);
        assertEquals(eightCharacterLoginUser, service.register(eightCharacterLoginUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        User emptyPasswordUser = new User();
        emptyPasswordUser.setLogin("dianazotsenko");
        emptyPasswordUser.setPassword("");
        emptyPasswordUser.setAge(19);
        String expectedMessage = "Password must be at least " + MIN_LENGTH + " characters long.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(emptyPasswordUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_threeCharacterPassword_notOk() {
        User threeCharacterPasswordUser = new User();
        threeCharacterPasswordUser.setLogin("dianazotsenko");
        threeCharacterPasswordUser.setPassword("tes");
        threeCharacterPasswordUser.setAge(19);
        String expectedMessage = "Password must be at least " + MIN_LENGTH + " characters long.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(threeCharacterPasswordUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_fiveCharacterPassword_notOk() {
        User fivecharacterPasswordUser = new User();
        fivecharacterPasswordUser.setLogin("dianazotsenko");
        fivecharacterPasswordUser.setPassword("testp");
        fivecharacterPasswordUser.setAge(19);
        String expectedMessage = "Password must be at least " + MIN_LENGTH + " characters long.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(fivecharacterPasswordUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_sixCharacterPassword_ok() {
        User sixCharacterPasswordUser = new User();
        sixCharacterPasswordUser.setLogin("dianazotsenko");
        sixCharacterPasswordUser.setPassword("testpa");
        sixCharacterPasswordUser.setAge(19);
        assertEquals(sixCharacterPasswordUser, service.register(sixCharacterPasswordUser));
    }

    @Test
    void register_eightCharacterPassword_ok() {
        User eightCharacterPasswordUser = new User();
        eightCharacterPasswordUser.setLogin("mariapetrenko");
        eightCharacterPasswordUser.setPassword("testpass");
        eightCharacterPasswordUser.setAge(19);
        assertEquals(eightCharacterPasswordUser, service.register(eightCharacterPasswordUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User negativeAgeUser = new User();
        negativeAgeUser.setLogin("alicewhite");
        negativeAgeUser.setPassword("testpassword");
        negativeAgeUser.setAge(-5);
        String expectedMessage = "User's age must be at least" + MIN_AGE + " years.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(negativeAgeUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_zeroAge_notOk() {
        User zeroAgeUser = new User();
        zeroAgeUser.setLogin("alicewhite");
        zeroAgeUser.setPassword("testpassword");
        zeroAgeUser.setAge(0);
        String expectedMessage = "User's age must be at least" + MIN_AGE + " years.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(zeroAgeUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_underAge_notOk() {
        User underAgeUser = new User();
        underAgeUser.setLogin("alicewhite");
        underAgeUser.setPassword("testpassword");
        underAgeUser.setAge(10);
        String expectedMessage = "User's age must be at least" + MIN_AGE + " years.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(underAgeUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void register_eighteenAge_Ok() {
        User eighteenAgeUser = new User();
        eighteenAgeUser.setLogin("alicewhite");
        eighteenAgeUser.setPassword("testpassword");
        eighteenAgeUser.setAge(18);
        assertEquals(eighteenAgeUser, service.register(eighteenAgeUser));
    }

    @Test
    void register_overAge_Ok() {
        User overAgeUser = new User();
        overAgeUser.setLogin("john12345");
        overAgeUser.setPassword("testpassword");
        overAgeUser.setAge(30);
        assertEquals(overAgeUser, service.register(overAgeUser));
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        User firstUser = new User();
        firstUser.setLogin("dianazotsenko");
        firstUser.setPassword("testpassword");
        firstUser.setAge(19);
        User sameSecondUser = new User();
        sameSecondUser.setLogin("dianazotsenko");
        sameSecondUser.setPassword("testpassword");
        sameSecondUser.setAge(19);
        assertEquals(firstUser, service.register(firstUser));
        String expectedMessage = "User with login '" + sameSecondUser.getLogin()
                + "' already exists.";
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> service.register(sameSecondUser));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
