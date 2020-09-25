package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class FQQuesoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FQQueso.class);
        FQQueso fQQueso1 = new FQQueso();
        fQQueso1.setId(1L);
        FQQueso fQQueso2 = new FQQueso();
        fQQueso2.setId(fQQueso1.getId());
        assertThat(fQQueso1).isEqualTo(fQQueso2);
        fQQueso2.setId(2L);
        assertThat(fQQueso1).isNotEqualTo(fQQueso2);
        fQQueso1.setId(null);
        assertThat(fQQueso1).isNotEqualTo(fQQueso2);
    }
}
