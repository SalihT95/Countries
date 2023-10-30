package com.turkoglu.countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turkoglu.countries.model.Country
import com.turkoglu.countries.service.CountryAPIService
import com.turkoglu.countries.service.CountryDatabase
import com.turkoglu.countries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application){
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10*60*1000*1000*1000L // 10 dk geçtikten sonra api den veri çekmek için süreyi ayarlıyorum

    //gözlemleyebilmek için mutable kullanıyorum
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){

        val updateTime = customPreferences.getTime()

        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }
    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite(){
        //countryLoading.value=true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            //Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG ).show()
        }
    }
    private fun getDataFromAPI(){

        countryLoading.value=true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                       storeInSQLite(t)
                        //Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG ).show()

                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value=false
                        countryError.value=true
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun showCountries(countryList: List<Country>){
        countries.value= countryList
        countryError.value=false
        countryLoading.value=false
    }

    private fun storeInSQLite(countryList: List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*countryList.toTypedArray()) // list -> individual

            var i = 0
            while (i< countryList.size){
                countryList[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(countryList)


        }

        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}