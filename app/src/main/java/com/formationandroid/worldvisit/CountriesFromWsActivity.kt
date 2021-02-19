package com.formationandroid.worldvisit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.formationandroid.worldvisit.ws.NetworkHelper
import com.formationandroid.worldvisit.ws.RetrofitSingleton
import com.formationandroid.worldvisit.ws.ReturnWSCountry
import com.formationandroid.worldvisit.ws.WSInterface
import kotlinx.android.synthetic.main.activity_countries_from_ws.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesFromWsActivity : AppCompatActivity() {
    private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)
    private var countriesAdapterFromWs: CountriesAdapterFromWs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_from_ws)

        // If the user is not connected to internet we display him a toast of error
        if (!NetworkHelper.isConnectedToNetwork(this))
        {
            Toast.makeText(this, R.string.main_error_internet_connection, Toast.LENGTH_LONG).show()
        }
        else {
            // Make the spinner visible so we see it when we are calling the WS
            progress_bar.visibility = View.VISIBLE

            // Calling the Web Service
            this.callingWebService(serviceRetrofit.getCountries())
        }
    }

    private fun callingWebService(call: Call<MutableList<ReturnWSCountry>>) {
        call.enqueue(object : Callback<MutableList<ReturnWSCountry>>
        {
            override fun onResponse(
                call: Call<MutableList<ReturnWSCountry>>,
                response: Response<MutableList<ReturnWSCountry>>
            ) {
                // If the http request is successful
                if (response.isSuccessful)
                {
                    // Reading the body
                    val returnWSCountries = response.body()

                    val layoutManager = LinearLayoutManager(this@CountriesFromWsActivity)
                    list_countries_from_ws.layoutManager = layoutManager

                    // Settings the list of values
                    countriesAdapterFromWs = returnWSCountries?.let { CountriesAdapterFromWs(it) }
                    list_countries_from_ws.adapter = countriesAdapterFromWs

                    // Notify the view to say the list changed (doesn't refresh without this)
                    countriesAdapterFromWs?.notifyDataSetChanged()

                    // Remove the progress bar since we've set the list
                    progress_bar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<ReturnWSCountry>>, t: Throwable)
            {
                // Since it goes wrong we set a toast to inform the user and then we remove the progress bar since we are done
                Toast.makeText(this@CountriesFromWsActivity, R.string.main_error_connection_ws, Toast.LENGTH_LONG).show()
                progress_bar.visibility = View.GONE
            }
        })
    }

    fun searchName(view: View) {
        // If the user is not connected to internet we display him a toast of error
        if (!NetworkHelper.isConnectedToNetwork(this))
        {
            Toast.makeText(this, R.string.main_error_internet_connection, Toast.LENGTH_LONG).show()
        }
        else {
            // Make the spinner visible so we see it when we are calling the WS
            progress_bar.visibility = View.VISIBLE

            // Calling the Web Service
            this.callingWebService(serviceRetrofit.getCountries("https://restcountries.eu/rest/v2/name/" + editText.text + "?fields=name;capital;region;alpha2Code"))
        }
    }
}