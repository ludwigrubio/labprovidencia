package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class PruebaMicroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PruebaMicro.class);
        PruebaMicro pruebaMicro1 = new PruebaMicro();
        pruebaMicro1.setId(1L);
        PruebaMicro pruebaMicro2 = new PruebaMicro();
        pruebaMicro2.setId(pruebaMicro1.getId());
        assertThat(pruebaMicro1).isEqualTo(pruebaMicro2);
        pruebaMicro2.setId(2L);
        assertThat(pruebaMicro1).isNotEqualTo(pruebaMicro2);
        pruebaMicro1.setId(null);
        assertThat(pruebaMicro1).isNotEqualTo(pruebaMicro2);
    }
}
