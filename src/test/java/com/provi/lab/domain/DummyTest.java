package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class DummyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dummy.class);
        Dummy dummy1 = new Dummy();
        dummy1.setId(1L);
        Dummy dummy2 = new Dummy();
        dummy2.setId(dummy1.getId());
        assertThat(dummy1).isEqualTo(dummy2);
        dummy2.setId(2L);
        assertThat(dummy1).isNotEqualTo(dummy2);
        dummy1.setId(null);
        assertThat(dummy1).isNotEqualTo(dummy2);
    }
}
