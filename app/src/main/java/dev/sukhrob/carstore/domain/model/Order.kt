package dev.sukhrob.carstore.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import dev.sukhrob.carstore.utils.Constants
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Order(
    var orderId: Long? = null,
    var customerFullName: String? = null,
    var customerPhoneNumber: String? = null,
    var carId: String? = null,
    var color: String? = null,
    var engineType: String? = null,
    var remoteController: Boolean? = null,
    var soundproof: Boolean? = null,
    var sunroof: Boolean? = null,
    var tinting: Boolean? = null,
    var videoRecorder: Boolean? = null,
    var wheelDisk: Boolean? = null,
    var purchasedPrice: Long? = null
): Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            Constants.orderId to orderId,
            Constants.carId to carId,
            Constants.color to color,
            Constants.engineType to engineType,
            Constants.soundproof to soundproof,
            Constants.sunroof to sunroof,
            Constants.tinting to tinting,
            Constants.videoRecorder to videoRecorder,
            Constants.wheelDisk to wheelDisk,
            Constants.purchasedPrice to purchasedPrice
        )
    }
}