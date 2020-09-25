package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class FQMantequillaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FQMantequilla.class);
        FQMantequilla fQMantequilla1 = new FQMantequilla();
        fQMantequilla1.setId(1L);
        FQMantequilla fQMantequilla2 = new FQMantequilla();
        fQMantequilla2.setId(fQMantequilla1.getId());
        assertThat(fQMantequilla1).isEqualTo(fQMantequilla2);
        fQMantequilla2.setId(2L);
        assertThat(fQMantequilla1).isNotEqualTo(fQMantequilla2);
        fQMantequilla1.setId(null);
        assertThat(fQMantequilla1).isNotEqualTo(fQMantequilla2);
    }
}
