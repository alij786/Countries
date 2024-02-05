package com.example.countries.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.CountryLayoutBinding
import com.example.countries.entities.Countries
import com.example.countries.entities.Country

class CountriesAdapter(private var countries: Countries = listOf()) :
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    fun setData(data: Countries) {
        notifyItemRangeRemoved(0, countries.size)
        countries = data
        notifyItemRangeInserted(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run { CountryViewHolder(root) }
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemBinding: CountryLayoutBinding = CountryLayoutBinding.bind(view)

        fun bind(country: Country) {
            with(itemBinding) {
                countryName.text =
                    itemView.context.getString(R.string.country_name, country.name, country.region)
                countryCode.text = country.code
                countryCapitol.text = country.capital
            }
        }
    }
}
