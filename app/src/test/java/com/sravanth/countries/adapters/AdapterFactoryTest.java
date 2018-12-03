package com.sravanth.countries.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.sravanth.countries.R;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.wrappers.ButterknifeWrapper;
import com.sravanth.countries.wrappers.Navigator;
import com.sravanth.countries.wrappers.SVGWrapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdapterFactoryTest {

    @Before
    public void setUp()
    {
        initMocks( this );

        adapterFactory = new AdapterFactory( activity,
                                             butterknifeWrapper,
                                             androidWrapper,
                                             navigator,
                                             svgWrapper,
                                             resources,
                                             R.drawable.ic_launcher_background);
    }


    @Test
    public void testGetCountriesAdapter()
    {
        assertThat( adapterFactory.getCountriesAdapter(  new ArrayList<>() ) ).isNotNull();
    }
    @Test
    public void testGetBasicItemDecoration()
    {
        assertThat( adapterFactory.getBasicItemDecoration( mock( Context.class ), false ) ).isNotNull();
    }

    private AdapterFactory adapterFactory;
    @Mock private Activity activity;
    @Mock private AndroidWrapper androidWrapper;
    @Mock private ButterknifeWrapper butterknifeWrapper;
    @Mock private Navigator navigator;
    @Mock private SVGWrapper svgWrapper;
    @Mock private Resources resources;



}