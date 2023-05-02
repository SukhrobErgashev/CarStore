package dev.sukhrob.carstore.domain.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import dev.sukhrob.carstore.utils.Constants

@IgnoreExtraProperties
data class Car(
    var carId: String? = null,
    var model: String? = null,
    var carImage: String? = null,
    var originCountry: String? = null,
    var originYear: String? = null,
    var color: String? = null,
    var price: Long? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            Constants.carId to carId,
            Constants.model to model,
            Constants.originYear to originCountry,
            Constants.originYear to originYear,
            Constants.color to color,
            Constants.price to price
        )
    }
}