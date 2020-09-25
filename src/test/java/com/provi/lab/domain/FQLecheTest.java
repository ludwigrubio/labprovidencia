package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class FQLecheTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FQLeche.class);
        FQLeche fQLeche1 = new FQLeche();
        fQLeche1.setId(1L);
        FQLeche fQLeche2 = new FQLeche();
        fQLeche2.setId(fQLeche1.getId());
        assertThat(fQLeche1).isEqualTo(fQLeche2);
        fQLeche2.setId(2L);
        assertThat(fQLeche1).isNotEqualTo(fQLeche2);
        fQLeche1.setId(null);
        assertThat(fQLeche1).isNotEqualTo(fQLeche2);
    }
}
