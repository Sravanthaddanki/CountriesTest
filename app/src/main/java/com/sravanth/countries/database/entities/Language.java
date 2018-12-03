package com.sravanth.countries.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "languages")
public class Language implements Parcelable{

    public Language()
    {

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    protected Language(@NonNull Parcel in) {

        iso639_1 = in.readString();
        iso639_2 = in.readString();
        name = in.readString();
        nativeName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(iso639_1);
        parcel.writeString(iso639_2);
        parcel.writeString(name);
        parcel.writeString(nativeName);

    }

    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
        @NonNull
        @Override
        public Language createFromParcel(@NonNull Parcel source) {
            return new Language(source);
        }

        @NonNull
        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo ( name="CountryId")
    private long countryId;
    @SerializedName("iso639_1")  @ColumnInfo(name = "iso639_1")
    private String iso639_1;

    @SerializedName("iso639_2") @ColumnInfo(name = "iso639_2")
    private String iso639_2;
    @SerializedName("name")  @ColumnInfo(name = "name")
    private String name;
    @SerializedName("nativeName")  @ColumnInfo(name = "nativeName")
    private String nativeName;
}
