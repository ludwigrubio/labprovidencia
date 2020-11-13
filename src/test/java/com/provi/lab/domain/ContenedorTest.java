package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class ContenedorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contenedor.class);
        Contenedor contenedor1 = new Contenedor();
        contenedor1.setId(1L);
        Contenedor contenedor2 = new Contenedor();
        contenedor2.setId(contenedor1.getId());
        assertThat(contenedor1).isEqualTo(contenedor2);
        contenedor2.setId(2L);
        assertThat(contenedor1).isNotEqualTo(contenedor2);
        contenedor1.setId(null);
        assertThat(contenedor1).isNotEqualTo(contenedor2);
    }
}
