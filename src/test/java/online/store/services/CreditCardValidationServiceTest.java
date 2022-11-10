package online.store.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import online.store.exceptions.CreditCardValidationException;

@ExtendWith(MockitoExtension.class)
public class CreditCardValidationServiceTest {
    
    @Mock
    CreditCardValidationService creditCardValidationService;


    @Test
    void testValidate() {

        creditCardValidationService.validate("1234567891011121");

        verify(creditCardValidationService).validate(anyString());
    }

    @Test
    void testValidateWithStolenOrInvalidCreditCard() {
        doThrow(new CreditCardValidationException("Boom")).when(creditCardValidationService).validate(anyString());

        assertThrows(CreditCardValidationException.class, 
            ()->creditCardValidationService.validate(new String()));

            verify(creditCardValidationService).validate(anyString());
    }
}
