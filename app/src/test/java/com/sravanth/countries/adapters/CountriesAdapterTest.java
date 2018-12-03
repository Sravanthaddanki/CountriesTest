package com.sravanth.countries.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sravanth.countries.R;
import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.wrappers.ButterknifeWrapper;
import com.sravanth.countries.wrappers.Navigator;
import com.sravanth.countries.wrappers.SVGWrapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CountriesAdapterTest {

    @Before
    public void setUp()
    {
        initMocks( this );
        countries = new ArrayList<>();

        Country country1 = new Country();
        country1.setName("india");
        countries.add( country1 );

        Country country2 = new Country();
        country2.setName("china");
        countries.add( country2 );

        Country country3 = new Country();
        country3.setName("japan");
        countries.add( country3 );

        when( activity.getLayoutInflater() ).thenReturn( layoutInflater );
        when( activity.getResources() ).thenReturn( resources );
        adapter = new CountriesAdapter( activity,
                countries,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                androidWrapper,
                butterknifeWrapper,
                svgWrapper,
                navigator);
    }

    @Test
    public void testOnCreateViewHolder()
    {
        adapter = spy(new CountriesAdapter(activity,
                countries,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                androidWrapper,
                butterknifeWrapper,
                svgWrapper,
                navigator) );

        View view = mock( View.class );
        ViewGroup parent = mock( ViewGroup.class );
        CountriesAdapter.CountryViewHolder holder = mock( CountriesAdapter.CountryViewHolder.class );
        ConstraintLayout constraintLayout = mock( ConstraintLayout.class );
        doReturn( holder ).when( adapter ).getNewCountryViewHolder( view, butterknifeWrapper );
        holder.countryInfoLayout = constraintLayout;
        when( view.findViewById( R.id.country_information_section ) ).thenReturn( constraintLayout );
        when( layoutInflater.inflate( R.layout.list_item_countries, parent, false ) ).thenReturn( view );

        adapter.onCreateViewHolder( parent, 0 );

        verify( constraintLayout ).setOnClickListener( any() );
        verify( constraintLayout ).setTag( holder );
        assertThat( adapter.onCreateViewHolder( parent, 0 ) ).isNotNull();
    }

    @Test
    public void testGetItemCount()
    {
        assertThat( adapter.getItemCount() ).isEqualTo( 3 );
    }

    @Test
    public void testOnBindViewHolder_New_WithMiles()
    {
        CountriesAdapter.CountryViewHolder holder = setUpViewHolder();
        when( svgWrapper.with( activity ) ).thenReturn( svgWrapper );
        when( svgWrapper.setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher )).thenReturn( svgWrapper );
        when( svgWrapper.load(" http://flag", holder.flag) ).thenReturn( svgWrapper );
        adapter.onBindViewHolder( holder, 0 );
        verify( holder.countryName ).setText( "india" );
    }


    @Test
    public void testAdd()
    {
        assertThat( countries.size() ).isEqualTo( 3 );
        adapter.add( new Country() );
        assertThat( countries.size() ).isEqualTo( 4 );
    }

    @Test
    public void testAddAll()
    {
        List<Country> newList = new ArrayList<>();
        newList.add( new Country() );
        newList.add( new Country() );
        newList.add( new Country() );
        assertThat( countries.size() ).isEqualTo( 3 );
        adapter.addAll( newList );
        assertThat( countries.size() ).isEqualTo( 6 );
    }

    @Test
    public void testClearAll()
    {
        assertThat( countries.size() ).isEqualTo( 3 );
        adapter.clearAll();
        assertThat( countries.size() ).isEqualTo( 0 );
    }

    @Test
    public void testGetNewDealerInventoryViewHolder()
    {
        CountriesAdapter.CountryViewHolder countryViewHolder = adapter.getNewCountryViewHolder( view,butterknifeWrapper );
        verify( butterknifeWrapper ).bind( countryViewHolder,view );
    }

    private CountriesAdapter.CountryViewHolder setUpViewHolder()
    {
        CountriesAdapter.CountryViewHolder holder = new CountriesAdapter.CountryViewHolder( view, butterknifeWrapper );
        holder.flag = flag;
        holder.countryName = countryName;
        holder.countryInfoLayout = countryInfoLayout;
        return holder;
    }

    private CountriesAdapter adapter;
    private List<Country> countries;
    @Mock private Activity activity;
    @Mock private ImageView flag;
    @Mock private TextView countryName;
    @Mock private ConstraintLayout countryInfoLayout;
    @Mock private ButterknifeWrapper butterknifeWrapper;
    @Mock private SVGWrapper svgWrapper;
    @Mock private AndroidWrapper androidWrapper;
    @Mock private Navigator navigator;
    @Mock private LayoutInflater layoutInflater;
    @Mock private Resources resources;
    @Mock private View view;
}