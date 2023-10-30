package com.turkoglu.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turkoglu.countries.R
import com.turkoglu.countries.databinding.FragmentCountryBinding
import com.turkoglu.countries.databinding.FragmentFeedBinding
import com.turkoglu.countries.util.downloadFromUrl
import com.turkoglu.countries.util.placeholderProgresBar
import com.turkoglu.countries.viewmodel.CountryViewModel

// TODO: Rename parameter arguments, choose names that match

class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding

    private lateinit var viewModel: CountryViewModel
    private var countryUuid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)


        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer{country->
            country?.let {
                binding.selectedCountry = country


                /*
                binding.countryCapital.text=country.countryCapital
                binding.countryCurrency.text=country.countryCurrency
                binding.countryLanguage.text=country.countryLanguage
                binding.countryName.text=country.countryName
                binding.countryRegion.text=country.countryRegion
                context?.let {
                    binding.countryImage.downloadFromUrl(country.imageUrl, placeholderProgresBar(it))
                }
                */


            }

        })

    }
}