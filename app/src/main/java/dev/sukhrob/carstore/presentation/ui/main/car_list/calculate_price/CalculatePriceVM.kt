package dev.sukhrob.carstore.presentation.ui.main.car_list.calculate_price

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.carstore.domain.model.Order
import dev.sukhrob.carstore.utils.calculateDiscount
import dev.sukhrob.carstore.utils.calculateLoan
import dev.sukhrob.carstore.utils.calculateSum2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatePriceVM @Inject constructor(): ViewModel() {

    private var _payByCash = MutableStateFlow(true)
    val payByCash: StateFlow<Boolean> get() = _payByCash

    private var _loanDuration = MutableStateFlow(6)
    val loanDuration: StateFlow<Int> get() = _loanDuration

    private var _discount = MutableStateFlow(1)
    val discount: StateFlow<Int> get() = _discount

    private var counter = MutableStateFlow(1)

    private var _totalPrice = MutableStateFlow(0L)
    val totalPrice: StateFlow<Long> get() = _totalPrice

    fun calculateTotalPrice(price: Long) {
        if (_payByCash.value) {
            calculateSum(price, 0, 0, true, counter.value, false)
        } else {
            calculateSum(
                price,
                _loanDuration.value,
                24,
                false,
                counter.value,
                false
            )
        }
    }


    private fun calculateSum(
        price: Long,
        duration: Int,
        percent: Int,
        isByCash: Boolean,
        count: Int,
        privilege: Boolean
    ) {
        if (isByCash) {
            val sum = calculateSum2(price, discount.value, count)
            if (privilege) {
                _totalPrice.value = (sum - sum * 5 / 100).toLong()

            } else {
                _totalPrice.value = (sum).toLong()
            }

        } else {
            val loan = calculateLoan(price, duration, percent, discount.value, count)

            if (privilege) {
                _totalPrice.value = (loan - loan * 5 / 100).toLong()
            } else {
                _totalPrice.value = loan.toLong()
            }
        }

    }


    fun setPayByCash(state: Boolean) {
        _payByCash.value = state
    }

    fun setLoanDuration(state: Int) {
        _loanDuration.value = state
    }

    fun setDiscount(state: Int) {
        val tempDiscount = calculateDiscount(state)
        _discount.value = tempDiscount
    }

    fun setTotalPrice(price: Long) {
        _totalPrice.value = price
    }

}