package sandbox;

import com.jskno.validationapp.persistence.model.Booking;
import com.jskno.validationapp.persistence.model.CARD_TYPE;
import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.service.BookingService;
import com.jskno.validationapp.service.impl.BookingServiceImpl;
import com.jskno.validationapp.web.dto.BookingDTO;
import com.jskno.validationapp.web.dto.CustomerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Set;

public class ProgrammaticValidationTest {

    public static final Logger LOG = LoggerFactory.getLogger(ProgrammaticValidationTest.class);

    private ExecutableValidator executableValidator;

    @BeforeEach
    public void getExecutableValidator() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.executableValidator = factory.getValidator().forExecutables();
    }

    @Test
    public void testingProgrammaticValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCardType(CARD_TYPE.DNI);
        customerDTO.setEmail("whatever");
        customerDTO.setDateOfBirth(LocalDate.now().plusDays(5));

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customerDTO);
        violations.forEach(violation -> LOG.error(violation.getMessage()));
        Assertions.assertEquals(violations.size(), 5);
    }

    @Test
    public void whenValidationWithInvalidConstructorReturnValue_thenCorrectNumberOfViolations() throws NoSuchMethodException {

        Constructor<Booking> constructor = Booking.class.getConstructor(Long.class, LocalDate.class, LocalDate.class, Customer.class);
        Booking createdObject = new Booking(1L, LocalDate.now(), LocalDate.now(), new Customer());
        Set<ConstraintViolation<Booking>> violations = executableValidator.validateConstructorReturnValue(constructor, createdObject);

        Assertions.assertEquals(1, violations.size());
    }





}
