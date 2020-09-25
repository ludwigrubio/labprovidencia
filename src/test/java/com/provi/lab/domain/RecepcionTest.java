package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class RecepcionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recepcion.class);
        Recepcion recepcion1 = new Recepcion();
        recepcion1.setId(1L);
        Recepcion recepcion2 = new Recepcion();
        recepcion2.setId(recepcion1.getId());
        assertThat(recepcion1).isEqualTo(recepcion2);
        recepcion2.setId(2L);
        assertThat(recepcion1).isNotEqualTo(recepcion2);
        recepcion1.setId(null);
        assertThat(recepcion1).isNotEqualTo(recepcion2);
    }
}
