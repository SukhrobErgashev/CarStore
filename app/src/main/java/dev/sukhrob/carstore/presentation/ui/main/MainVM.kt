package dev.sukhrob.carstore.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.sukhrob.carstore.domain.model.Car
import dev.sukhrob.carstore.domain.model.Manager
import dev.sukhrob.carstore.domain.model.Order
import dev.sukhrob.carstore.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import kotlin.collections.ArrayList

class MainVM : ViewModel() {

    private var db = Firebase.firestore
    private var auth = Firebase.auth

    private val cars = "cars"

    private var _carList = MutableStateFlow<List<Car>>(emptyList())
    val carList: StateFlow<List<Car>> get() = _carList

    private var _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders

    private var _carById = MutableStateFlow(Order())
    val carById: StateFlow<Order> get() = _carById

    private var _managerInfo = MutableStateFlow(Manager())
    val managerInfo: StateFlow<Manager> get() = _managerInfo

    init {
        getCarList()
    }

    fun gerManagerInfo() {
        db.collection("managers").document(auth.uid!!).get().addOnSuccessListener { result ->
            _managerInfo.value = result.toObject(Manager::class.java) ?: Manager()
        }.addOnFailureListener { exception ->
            Log.d("something", "Error getting manager info")
        }
    }

    fun getOrders() {
        val orders = ArrayList<Order>()
        db.collection("managers").document(auth.uid!!).collection("orders")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val order = document.toObject(Order::class.java)
                    orders.add(order)
                }
                _orders.value = orders
            }.addOnFailureListener { exception ->
                Log.d("something", "Error getting orders")
            }
    }

    fun getCarById(carId: String) {
        db.collection("cars").document(carId).get().addOnSuccessListener {
            _carById.value = it.toObject(Order::class.java) ?: Order()
        }
    }

    fun getCarList() {
        val carsRef = db.collection(cars)
        val cars = ArrayList<Car>()
        carsRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val car = document.toObject(Car::class.java)
                    Log.d("something", car.model.toString())
                    cars.add(car)
                }
                _carList.value = cars
            }
            .addOnFailureListener { exception ->
                Log.d("something", "Error getting cars: ", exception)
            }
//        docRef.get().addOnSuccessListener {
//            val cars = ArrayList<Car>()
//            for (item in it.documents) {
//                val car = Car()
//
////                car.carId = (item.data!![Constants.carId] as String).toInt()
//                car.model = item.data!![Constants.model] as String
//                car.carImage = item.data!![Constants.carImage] as String
//                car.originCountry = item.data!![Constants.originCountry] as String?
//                car.originYear = item.data!![Constants.originCountry] as String?
//                car.color = item.data!![Constants.color] as String?
//                car.price = item.data!![Constants.price] as Long?
//            }
//
//            _carList.value = cars
//        }.addOnFailureListener {
//            Log.d("get", it.localizedMessage!!)
//            _carList.value = emptyList()
//        }
    }

    fun create(car: Car) {
        auth.currentUser?.let {
            val docRef = db.collection("users").document(auth.currentUser!!.uid).collection(cars)
            docRef.document(
                Calendar.getInstance().timeInMillis.toString()
            ).set(
                car
            ).addOnSuccessListener {
                Log.d("hohoba", "successfull")
            }.addOnFailureListener {
                Log.d("hohoba", "failed")
            }
        }

    }

    fun insertOrder(order: Order) {
        db.collection("managers")
            .document(auth.currentUser!!.uid)
            .collection("orders")
            .document(
                Calendar.getInstance().timeInMillis.toString()
            ).set(
                order
            ).addOnSuccessListener {
                Log.d("hohoba", "successfull")
            }.addOnFailureListener {
                Log.d("hohoba", "failed")
            }
    }




}