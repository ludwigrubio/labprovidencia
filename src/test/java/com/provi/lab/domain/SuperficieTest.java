package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class SuperficieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Superficie.class);
        Superficie superficie1 = new Superficie();
        superficie1.setId(1L);
        Superficie superficie2 = new Superficie();
        superficie2.setId(superficie1.getId());
        assertThat(superficie1).isEqualTo(superficie2);
        superficie2.setId(2L);
        assertThat(superficie1).isNotEqualTo(superficie2);
        superficie1.setId(null);
        assertThat(superficie1).isNotEqualTo(superficie2);
    }
}
