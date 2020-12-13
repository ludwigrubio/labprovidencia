package com.provi.lab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.provi.lab.web.rest.TestUtil;

public class LogLactoEscanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogLactoEscan.class);
        LogLactoEscan logLactoEscan1 = new LogLactoEscan();
        logLactoEscan1.setId(1L);
        LogLactoEscan logLactoEscan2 = new LogLactoEscan();
        logLactoEscan2.setId(logLactoEscan1.getId());
        assertThat(logLactoEscan1).isEqualTo(logLactoEscan2);
        logLactoEscan2.setId(2L);
        assertThat(logLactoEscan1).isNotEqualTo(logLactoEscan2);
        logLactoEscan1.setId(null);
        assertThat(logLactoEscan1).isNotEqualTo(logLactoEscan2);
    }
}
