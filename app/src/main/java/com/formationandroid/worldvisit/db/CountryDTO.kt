package com.formationandroid.worldvisit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*


@TypeConverters(Converters::class)
@Entity(tableName = "countries")
class CountryDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val capital: String,
    val region: String,
    val code: String,
    val visitDate: Date?
)