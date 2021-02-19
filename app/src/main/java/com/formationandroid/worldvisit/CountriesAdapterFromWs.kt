package com.formationandroid.worldvisit

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.worldvisit.ws.ReturnWSCountry
import com.squareup.picasso.Picasso

class CountriesAdapterFromWs(private var listCountry: MutableList<ReturnWSCountry>) :
    RecyclerView.Adapter<CountriesAdapterFromWs.CountryFromWsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryFromWsViewHolder
    {
        val viewCourse = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country_from_ws, parent, false)
        return CountryFromWsViewHolder(viewCourse)
    }

    override fun onBindViewHolder(holder: CountryFromWsViewHolder, position: Int)
    {
        holder.textCountryName.text = listCountry[position].name;
        holder.textCountryCapital.text = holder.itemView.context.getString(R.string.item_country_captial) + " " + listCountry[position].capital;
        holder.textCountryRegion.text = holder.itemView.context.getString(R.string.item_country_region) + " " + listCountry[position].region;
        holder.textCountryCode.text = listCountry[position].alpha2Code;

        Picasso.get()
            .load("http://www.geognos.com/api/en/countries/flag/" + listCountry[position].alpha2Code + ".png")
            .fit()
            .centerCrop()
            .into(holder.imageCountryFlag)
    }

    override fun getItemCount(): Int = listCountry.size

    inner class CountryFromWsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textCountryName: TextView = itemView.findViewById(R.id.country_name);
        val textCountryCapital: TextView = itemView.findViewById(R.id.country_capital);
        val textCountryRegion: TextView = itemView.findViewById(R.id.country_region);
        val imageCountryFlag: ImageView = itemView.findViewById(R.id.country_flag);
        val textCountryCode: TextView = itemView.findViewById(R.id.country_code);

        init
        {
            itemView.setOnClickListener {
                var intent = Intent(it.context, PickerActivity::class.java);
                intent.putExtra("countryName", textCountryName.text);
                intent.putExtra("countryRegion", textCountryRegion.text);
                intent.putExtra("countryCapital", textCountryCapital.text);
                intent.putExtra("countryCode", textCountryCode.text);

                it.context.startActivity(intent)
            }
        }
    }
}
