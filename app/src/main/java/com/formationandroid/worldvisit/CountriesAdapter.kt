package com.formationandroid.worldvisit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.worldvisit.db.AppDatabaseHelper
import com.formationandroid.worldvisit.db.CountryDTO
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CountriesAdapter(private var listCountry: MutableList<CountryDTO>) :
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder
    {
        val viewCourse = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(viewCourse)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int)
    {
        val dateFormatInput: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        val date: String = dateFormatInput.format(listCountry[position].visitDate)

        holder.textCountryName.text = listCountry[position].name;
        holder.textCountryCapital.text = listCountry[position].capital;
        holder.textCountryRegion.text = listCountry[position].region;
        holder.textVisitingDate.text = date;
        holder.textCountryCode.text = listCountry[position].code;
        holder.textId.text = listCountry[position].id.toString();

        Picasso.get()
            .load("http://www.geognos.com/api/en/countries/flag/" + listCountry[position].code + ".png")
            .fit()
            .centerCrop()
            .into(holder.imageCountryFlag)
    }

    fun removeItem(position: Int, it: View) {
        val countryVisit = listCountry[position]
        listCountry.removeAt(position)
        AppDatabaseHelper.getDatabase(it.context).countriesDAO().delete(countryVisit);
        notifyDataSetChanged();
    }

    override fun getItemCount(): Int = listCountry.size

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textCountryName: TextView = itemView.findViewById(R.id.country_name);
        val textCountryCapital: TextView = itemView.findViewById(R.id.country_capital);
        val textCountryRegion: TextView = itemView.findViewById(R.id.country_region);
        val imageCountryFlag: ImageView = itemView.findViewById(R.id.country_flag);
        val textVisitingDate: TextView = itemView.findViewById(R.id.visiting_date);
        val textCountryCode: TextView = itemView.findViewById(R.id.country_code);
        val textId: TextView = itemView.findViewById(R.id.country_id);
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteItemCountry);

        init {
            deleteButton.setOnClickListener() {
                removeItem(adapterPosition, it);
            }
        }
    }
}
