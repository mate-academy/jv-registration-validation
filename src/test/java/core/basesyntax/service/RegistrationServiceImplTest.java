package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationUser = new RegistrationServiceImpl();
    private User firstUser = new User();
    private User firstSameUser = new User();
    private User secondUser = new User();
    private User thirdUser = new User();
    private User wrongAgeUser = new User();
    private User wrongPassUser = new User();
    private User nullUser = null;
    private User nullAgeUser = new User();
    private User nullLoginUser = new User();
    private User nullPassUser = new User();

    @BeforeEach
    void setUp() {
        firstUser.setAge(18);
        firstUser.setLogin("First User");
        firstUser.setPassword("1234567");
        firstSameUser.setAge(18);
        firstSameUser.setLogin("First User");
        firstSameUser.setPassword("1234567");
        secondUser.setAge(57);
        secondUser.setLogin("Second User");
        secondUser.setPassword("345876906");
        thirdUser.setAge(57);
        thirdUser.setLogin("Third User");
        thirdUser.setPassword("345876906");
        wrongAgeUser.setAge(15);
        wrongAgeUser.setLogin("Wrong Age User");
        wrongAgeUser.setPassword("7654321");
        wrongPassUser.setAge(30);
        wrongPassUser.setLogin("Wrong Pass User");
        wrongPassUser.setPassword("1234");
        nullAgeUser.setAge(null);
        nullAgeUser.setLogin("Null Age User");
        nullAgeUser.setPassword("01928374");
        nullLoginUser.setAge(36);
        nullLoginUser.setLogin(null);
        nullLoginUser.setPassword("0987654");
        nullPassUser.setAge(56);
        nullPassUser.setLogin("Null Pass User");
        nullPassUser.setPassword(null);
    }

    @Test
    void register_nullParameters_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullUser));
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullAgeUser));
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullLoginUser));
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullPassUser));
    }

    @Test
    void register_wrongParameters_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(wrongAgeUser));
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(wrongPassUser));
    }

    @Test
    void register_sameLogin_notOk() {
        assertDoesNotThrow(() -> registrationUser.register(firstUser));
        assertThrows(RuntimeException.class,
                () -> registrationUser.register(firstSameUser));
    }

    @Test
    void register_allowablePass_Ok() {
        registrationUser.register(secondUser);
        registrationUser.register(thirdUser);
        assertEquals("345876906", secondUser.getPassword());
        assertEquals("345876906", thirdUser.getPassword());
        assertNotEquals("1234", secondUser.getPassword());
    }
}
