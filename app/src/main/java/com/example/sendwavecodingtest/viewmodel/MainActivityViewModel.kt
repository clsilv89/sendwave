package com.example.sendwavecodingtest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sendwavecodingtest.data.model.CountryEnum
import com.example.sendwavecodingtest.data.model.ExchangeResponseModel
import com.example.sendwavecodingtest.data.network.ResultWrapper
import com.example.sendwavecodingtest.usecases.IGetConversionUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxSingle

class MainActivityViewModel(
    private val getConversionUseCaseImpl: IGetConversionUseCase,
) : ViewModel() {
    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _firstName
    private val _LastName = MutableLiveData<String>()
    val lastName: MutableLiveData<String> get() = _LastName
    private val _ammount: MutableLiveData<Long> = MutableLiveData<Long>()
    val ammount: LiveData<String> get() = Transformations.map(_ammount) { it.toString() }
    private val _phoneNumber: MutableLiveData<String> = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber
    val mask: String = "8 (***) *** **-**"
    private val _maxPhoneLength: MutableLiveData<Int> = MutableLiveData<Int>()
    val maxPhoneLength: LiveData<Int> get() = _maxPhoneLength
    private val availableCountries = listOf("Kenya", "Nigeria", "Tanzania", "Uganda")
    private val _selectedCountry = MutableLiveData<CountryEnum>()

    fun setAmmount(ammount: String) {
        _ammount.postValue(ammount.toLong())
    }

    fun setFirstName(firstName: String) {
        _firstName.postValue(firstName)
    }

    fun setLastName(lastName: String) {
        _LastName.postValue(lastName)
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.postValue("${_selectedCountry.value?.countryPhonePrefix} $phoneNumber")
    }

    fun getAvailableCoutries(): List<String> {
        return availableCountries
    }

    fun setSelectedCountry(position: Int) {
        _selectedCountry.postValue(CountryEnum.get(availableCountries[position]))
        setMaxPhoneLength()
    }

    fun getConversion() = runBlocking {
        val result = rxSingle {
            getConversionUseCaseImpl.invoke()
        }.blockingGet()

        Log.d("Caio", result.toString())
        when (result) {
            is ResultWrapper.NetworkError -> {
                Log.d("Caio", result.toString())
            }
            is ResultWrapper.GenericError -> {
                Log.d("Caio", result.toString())
            }
            is ResultWrapper.Success<ExchangeResponseModel> -> {
                Log.d("Caio", result.value.rateModels.exchangeRates.toString())
            }
        }
    }

    private fun setMaxPhoneLength() {
        _maxPhoneLength.postValue(
            when (_selectedCountry.value) {
                CountryEnum.KENYA, CountryEnum.TANZANIA -> 12
                CountryEnum.NIGERIA, CountryEnum.UGANDA -> 10
                else -> 12
            }
        )
    }

    private fun isValidPhoneNumber(phone: String) {
        if (phone.length == _maxPhoneLength.value) {

        }
    }
}
