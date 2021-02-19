package com.formationandroid.worldvisit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.formationandroid.worldvisit.db.AppDatabaseHelper
import com.formationandroid.worldvisit.db.CountryDTO
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PickerActivity : AppCompatActivity() {

    private lateinit var countryName: String;
    private lateinit var countryRegion: String;
    private lateinit var countryCapital: String;
    private var countryCode: String? = null;
    private lateinit var picker: DatePicker;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker)

        picker = findViewById(R.id.date_picker);

        countryName = intent.getStringExtra("countryName");
        countryRegion = intent.getStringExtra("countryRegion");
        countryCapital = intent.getStringExtra("countryCapital");
        countryCode = intent.getStringExtra("countryCode");
    }

    fun validate(view: View) {
        var safeCountryCode = "";
        if (countryCode != null) {
            safeCountryCode = countryCode.toString();
        }

        val year = picker.year;
        val month = picker.month;
        val dayOfMonth = picker.dayOfMonth;

        val dateVisit = "$dayOfMonth-$month-$year";
        val dateFormatInput: DateFormat = SimpleDateFormat("d-M-y", Locale.FRANCE);

        AppDatabaseHelper.getDatabase(this).countriesDAO().insert(
            CountryDTO(
                0.toLong(),
                countryName,
                countryCapital,
                countryRegion,
                safeCountryCode,
                dateFormatInput.parse(dateVisit)
            )
        )
        val intent = Intent(view.context, MainActivity::class.java);
        view.context.startActivity(intent);
    }
}