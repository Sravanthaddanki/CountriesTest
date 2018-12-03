package com.sravanth.countries.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.sravanth.countries.database.entities.Country;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CountriesDao {

    @Insert
    Single<Long> insert(Country country);

    @Query("SELECT * from countries WHERE countryName LIKE '%' || :name  || '%'")
    Single<List<Country>> getCountriesByName(String name);

}
