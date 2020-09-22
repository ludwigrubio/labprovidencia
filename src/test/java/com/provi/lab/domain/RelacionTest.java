package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class RelacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relacion.class);
        Relacion relacion1 = new Relacion();
        relacion1.setId(1L);
        Relacion relacion2 = new Relacion();
        relacion2.setId(relacion1.getId());
        assertThat(relacion1).isEqualTo(relacion2);
        relacion2.setId(2L);
        assertThat(relacion1).isNotEqualTo(relacion2);
        relacion1.setId(null);
        assertThat(relacion1).isNotEqualTo(relacion2);
    }
}
