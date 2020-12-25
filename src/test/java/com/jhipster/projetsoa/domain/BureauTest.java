package com.jhipster.projetsoa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.projetsoa.web.rest.TestUtil;

public class BureauTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bureau.class);
        Bureau bureau1 = new Bureau();
        bureau1.setId(1L);
        Bureau bureau2 = new Bureau();
        bureau2.setId(bureau1.getId());
        assertThat(bureau1).isEqualTo(bureau2);
        bureau2.setId(2L);
        assertThat(bureau1).isNotEqualTo(bureau2);
        bureau1.setId(null);
        assertThat(bureau1).isNotEqualTo(bureau2);
    }
}
