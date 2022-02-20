package com.caiosilva.sendwavecodingtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caiosilva.sendwavecodingtest.data.model.CountryEnum
import com.caiosilva.sendwavecodingtest.data.model.ExchangeResponseModel
import com.caiosilva.sendwavecodingtest.data.model.SendMoneyRequestModel
import com.caiosilva.sendwavecodingtest.data.network.ResultWrapper
import com.caiosilva.sendwavecodingtest.usecases.IGetConversionUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.rxSingle

class MainActivityViewModel(
    private val getConversionUseCaseImpl: IGetConversionUseCase,
) : ViewModel() {
    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _firstName
    private val _lastName = MutableLiveData<String>()
    val lastName: MutableLiveData<String> get() = _lastName
    private val _ammount: MutableLiveData<Long> = MutableLiveData<Long>()
    val ammount: LiveData<Long> get() = _ammount
    private val _phoneNumber: MutableLiveData<String> = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber
    private val _maxPhoneLength: MutableLiveData<Int> = MutableLiveData<Int>()
    val maxPhoneLength: LiveData<Int> get() = _maxPhoneLength
    private var _error = MutableLiveData<Boolean>().apply { value = false }
    val error: LiveData<Boolean> get() = _error
    private var _isLoading = MutableLiveData<Boolean>().apply { value = true }
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _valueToTransfer: MutableLiveData<String> = MutableLiveData<String>()
    val valueToTransfer: LiveData<String> get() = _valueToTransfer
    private val _selectedCountry = MutableLiveData<CountryEnum>()
    val selectedCountry: LiveData<CountryEnum> get() = _selectedCountry
    private val _exchangeResponseModel: MutableLiveData<ExchangeResponseModel> =
        MutableLiveData<ExchangeResponseModel>()
    val exchangeResponseModel: LiveData<ExchangeResponseModel> get() = _exchangeResponseModel
    private val _sendMoneyRequest: MutableLiveData<SendMoneyRequestModel> =
        MutableLiveData<SendMoneyRequestModel>()
    val sendMoneyRequest: LiveData<SendMoneyRequestModel> get() = _sendMoneyRequest

    private val availableCountries = listOf("Kenya", "Nigeria", "Tanzania", "Uganda")

    init {
        getExchangeValues()
    }

    fun setAmmount(ammount: Long) {
        _ammount.postValue(ammount)
    }

    fun setFirstName(firstName: String) {
        _firstName.postValue(firstName)
    }

    fun setLastName(lastName: String) {
        _lastName.postValue(lastName)
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

    private fun getExchangeValues() = runBlocking {
        _isLoading.postValue(true)
        val result = rxSingle {
            getConversionUseCaseImpl.invoke()
        }.blockingGet()

        when (result) {
            is ResultWrapper.NetworkError -> {
                _isLoading.postValue(false)
                _error.postValue(true)
            }
            is ResultWrapper.GenericError -> {
                _isLoading.postValue(false)
                _error.postValue(true)
            }
            is ResultWrapper.Success<ExchangeResponseModel> -> {
                _isLoading.postValue(false)
                _exchangeResponseModel.postValue(result.value)
            }
        }
    }

    fun calculateValuetoSend() {
        val rate = exchangeResponseModel.value?.rateModels?.find {
            it.currency == selectedCountry.value?.countryCode
        }?.rate ?: 0.0
        val ammountInDecimal = ammount.value?.toString()?.toInt(2) ?: 0
        val convertedValue = rate * ammountInDecimal
        _valueToTransfer.postValue(
            "${selectedCountry.value?.countryCode} ${Integer.toBinaryString(convertedValue.toInt())}"
        )
    }

    fun setupRequest() {
        _sendMoneyRequest.postValue(
            SendMoneyRequestModel(
                "${firstName.value} ${lastName.value}",
                phoneNumber.value.orEmpty(),
                valueToTransfer.value.orEmpty()
            )
        )
    }

    private fun setMaxPhoneLength() {
        _maxPhoneLength.postValue(
            when (_selectedCountry.value) {
                CountryEnum.KENYA, CountryEnum.TANZANIA -> 9
                CountryEnum.NIGERIA, CountryEnum.UGANDA -> 7
                else -> 9
            }
        )
    }
}
