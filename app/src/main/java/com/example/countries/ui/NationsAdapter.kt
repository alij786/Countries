package com.example.countries.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.NationLayoutBinding
import com.example.countries.entities.Nations
import com.example.countries.entities.Nation

class NationsAdapter(private var nations: Nations = listOf()) :
    RecyclerView.Adapter<NationsAdapter.NationViewHolder>() {

    fun setData(data: Nations) {
        notifyItemRangeRemoved(0, nations.size)
        nations = data
        notifyItemRangeInserted(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationViewHolder {
        return NationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run { NationViewHolder(root) }
    }

    override fun getItemCount(): Int {
        return nations.size
    }

    override fun onBindViewHolder(holder: NationViewHolder, position: Int) {
        holder.bind(nations[position])
    }

    class NationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemBinding: NationLayoutBinding = NationLayoutBinding.bind(view)

        fun bind(nation: Nation) {
            with(itemBinding) {
                nationName.text =
                    itemView.context.getString(R.string.nation_name, nation.nationName, nation.geographicalRegion)
                nationCode.text = nation.countryCode
                nationCapital.text = nation.capitalCity
            }
        }
    }
}
