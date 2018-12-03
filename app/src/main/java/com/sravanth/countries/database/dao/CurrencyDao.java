package com.sravanth.countries.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Observable;
import io.reactivex.Single;

import com.sravanth.countries.database.entities.Currency;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert
    void insert(Currency currency);

    @Query("SELECT * from currencies where countryId = :countryId")
    Single<List<Currency>> getCurrenciesByCountry(int countryId);

    @Insert
    void insertAll(List<Currency> currencies );

}
