package com.sravanth.countries.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Observable;
import io.reactivex.Single;

import com.sravanth.countries.database.entities.Language;

import java.util.List;

@Dao
public interface LanguageDao {

    @Insert
    void insert(Language language);

    @Query("SELECT * from languages where countryId = :countryId")
    Single<List<Language>>  getLanguagesByCountry(int countryId);

    @Insert
   void insertAll(List<Language> languages );

}
