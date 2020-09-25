package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class FQSueroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FQSuero.class);
        FQSuero fQSuero1 = new FQSuero();
        fQSuero1.setId(1L);
        FQSuero fQSuero2 = new FQSuero();
        fQSuero2.setId(fQSuero1.getId());
        assertThat(fQSuero1).isEqualTo(fQSuero2);
        fQSuero2.setId(2L);
        assertThat(fQSuero1).isNotEqualTo(fQSuero2);
        fQSuero1.setId(null);
        assertThat(fQSuero1).isNotEqualTo(fQSuero2);
    }
}
