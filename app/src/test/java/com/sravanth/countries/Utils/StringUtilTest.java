package com.sravanth.countries.Utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilTest {

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    public void testIsNullOrWhitespace_String()
    {
        assertThat( StringUtil.isNullOrWhitespace( (String) null ) ).isTrue();

        String str = "";

        assertThat( StringUtil.isNullOrWhitespace( str ) ).isTrue();

        str = " ";

        assertThat( StringUtil.isNullOrWhitespace( str ) ).isTrue();

        str = "123";

        assertThat( StringUtil.isNullOrWhitespace( str ) ).isFalse();
    }

}