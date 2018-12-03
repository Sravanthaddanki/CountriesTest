package com.sravanth.countries.database.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "countries")
public class Country implements Parcelable {
    public Country() {
    }


    @Ignore
    protected Country(@NonNull Parcel in) {

        id = in.readInt();
        name = in.readString();
        callingCodesList = in.createStringArrayList();
        callingCodes = in.readString();
        capital = in.readString();
        region = in.readString();
        subregion = in.readString();
        timezonesList = in.createStringArrayList();
        timezones = in.readString();
        flag = in.readString();
        flagPath = in.readString();
        currencies = in.createTypedArrayList(Currency.CREATOR);
        languages = in.createTypedArrayList(Language.CREATOR);
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("name")
    @ColumnInfo(name = "countryName")
    private String name;

    @Ignore
    @SerializedName("callingCodes")
    private ArrayList<String> callingCodesList;

    @ColumnInfo(name = "callingCodes")
    private String callingCodes;

    @SerializedName("capital")
    @ColumnInfo(name = "capital")
    private String capital;

    @SerializedName("region")
    @ColumnInfo(name = "region")
    private String region;

    @SerializedName("subregion")
    @ColumnInfo(name = "subregion")
    private String subregion;

    @Ignore
    @SerializedName("timezones")
    private ArrayList<String> timezonesList;

    @ColumnInfo(name = "timezones")
    private String timezones;

    @SerializedName("flag")
    @ColumnInfo(name = "flag")
    private String flag;

    @ColumnInfo(name = "flagpath")
    private String flagPath;

    @Ignore
    @SerializedName("currencies")
    private ArrayList<Currency> currencies;

    @Ignore
    @SerializedName("languages")
    private ArrayList<Language> languages;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeStringList(callingCodesList);
        parcel.writeString(callingCodes);
        parcel.writeString(capital);
        parcel.writeString(region);
        parcel.writeString(subregion);
        parcel.writeStringList(timezonesList);
        parcel.writeString(timezones);
        parcel.writeString(flag);
        parcel.writeString(flagPath);
        parcel.writeTypedList(currencies);
        parcel.writeTypedList(languages);

    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @NonNull
        @Override
        public Country createFromParcel(@NonNull Parcel source) {
            return new Country(source);
        }

        @NonNull
        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
