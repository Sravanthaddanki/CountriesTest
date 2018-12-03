package com.sravanth.countries.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import lombok.Data;

@Data
@Entity(tableName = "currencies")
public class Currency implements Parcelable{

    public Currency()
    {

    }
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo ( name="CountryId")
    private long countryId;
    @SerializedName("code")  @ColumnInfo(name = "code")
    private String code;

    @SerializedName("name")  @ColumnInfo(name = "name")
    private String name;
    @SerializedName("symbol")  @ColumnInfo(name = "symbol")
    private String symbol;

    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    protected Currency(@NonNull Parcel in) {

        code = in.readString();
        name = in.readString();
        symbol = in.readString();

    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString( code );
        parcel.writeString( name );
        parcel.writeString( symbol );

    }

    public static final Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>()
    {
        @NonNull
        @Override
        public Currency createFromParcel(@NonNull Parcel source )
        {
            return new Currency( source );
        }

        @NonNull
        @Override
        public Currency[] newArray(int size )
        {
            return new Currency[size];
        }
    };
}
