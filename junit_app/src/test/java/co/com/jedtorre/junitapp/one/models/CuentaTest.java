package co.com.jedtorre.junitapp.one.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Fulanito");
        cuenta.setSaldo(new BigDecimal(100));

        String esperado = "Fulanito";
        String real = cuenta.getPersona();

        assertEquals(esperado, real);
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Fulanito", new BigDecimal(1000));
        assertEquals(1000, cuenta.getSaldo().intValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Fulanito", new BigDecimal(1000));
        Cuenta cuenta2 = new Cuenta("Fulanito", new BigDecimal(1000));
        assertEquals(cuenta2, cuenta);
    }
}