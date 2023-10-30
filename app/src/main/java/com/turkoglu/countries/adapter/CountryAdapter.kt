package com.turkoglu.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.turkoglu.countries.R
import com.turkoglu.countries.databinding.ItemCountryBinding
import com.turkoglu.countries.model.Country
import com.turkoglu.countries.util.downloadFromUrl
import com.turkoglu.countries.util.placeholderProgresBar
import com.turkoglu.countries.view.FeedFragmentDirections
import java.util.zip.Inflater

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener{

    private lateinit var binding : ItemCountryBinding

    class CountryViewHolder(val view: ItemCountryBinding): RecyclerView.ViewHolder(view.root){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(binding)
    /*
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
 */


    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener= this



/*
        holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion

        holder.binding.root.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)

        }
        holder.binding.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgresBar(holder.binding.root.context))
 */

    }

    fun updateCountryList(newCountryList : List<Country>){

        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View,uuid:Int) {
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)

    }


}
