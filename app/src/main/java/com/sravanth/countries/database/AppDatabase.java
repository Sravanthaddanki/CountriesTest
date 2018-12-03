package com.sravanth.countries.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sravanth.countries.database.dao.CountriesDao;
import com.sravanth.countries.database.dao.CurrencyDao;
import com.sravanth.countries.database.dao.LanguageDao;
import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.database.entities.Currency;
import com.sravanth.countries.database.entities.Language;


@Database(entities = {Country.class, Language.class, Currency.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CountriesDao getCountriesDao();
    public abstract CurrencyDao getCurrencyDao();
    public abstract LanguageDao getLanguageDao();
}