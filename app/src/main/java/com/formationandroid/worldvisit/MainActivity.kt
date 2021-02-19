package com.formationandroid.worldvisit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.formationandroid.worldvisit.db.AppDatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_countries.setHasFixedSize(true);

        val layoutManager = LinearLayoutManager(this);
        list_countries.layoutManager = layoutManager;

        val listCountries = AppDatabaseHelper.getDatabase(this).countriesDAO().getListCountries();

        val countriesAdapter = CountriesAdapter(listCountries);
        list_countries.adapter = countriesAdapter;
    }

    override fun onBackPressed() { }

    fun addCountry(view: View) {
        val intent = Intent(this, CountriesFromWsActivity::class.java)
        startActivity(intent)
    }
}