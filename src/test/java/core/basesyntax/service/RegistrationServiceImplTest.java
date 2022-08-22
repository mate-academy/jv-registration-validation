package core.basesyntax.service;


import org.junit.jupiter.api.BeforeAll;


class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

}