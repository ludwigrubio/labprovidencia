package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class FQCremaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FQCrema.class);
        FQCrema fQCrema1 = new FQCrema();
        fQCrema1.setId(1L);
        FQCrema fQCrema2 = new FQCrema();
        fQCrema2.setId(fQCrema1.getId());
        assertThat(fQCrema1).isEqualTo(fQCrema2);
        fQCrema2.setId(2L);
        assertThat(fQCrema1).isNotEqualTo(fQCrema2);
        fQCrema1.setId(null);
        assertThat(fQCrema1).isNotEqualTo(fQCrema2);
    }
}
