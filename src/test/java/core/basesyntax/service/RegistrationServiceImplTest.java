package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceprions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void lengthLoginValid_NotOk() {
        user.setLogin("Login");
        user.setPassword("pass");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            service.register(user); });
    }

    @Test
    void ageValid_NotOk() {
        user.setLogin("Login123");
        user.setPassword("password");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> {
            service.register(user); });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            service.register(null); });
    }

    @Test
    void containsOnlySpacesInLogin_NotOk() {
        user.setLogin("          ");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            service.register(user); });
    }

    @Test
    void availabilityDuplicate_Ok() {
        User existing = new User();
        existing.setLogin("LoginDuplicate");
        existing.setPassword("password");
        existing.setAge(25);
        service.register(existing);

        user.setLogin("LoginDuplicate");
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            service.register(user); });
    }
}
