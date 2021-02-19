package com.formationandroid.worldvisit.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class CountryDAO {
    @Query("SELECT * FROM countries ORDER BY visitDate")
    abstract fun getListCountries(): MutableList<CountryDTO>

    @Insert
    abstract fun insert(vararg country: CountryDTO)

    @Delete
    abstract fun delete(vararg country: CountryDTO)
}