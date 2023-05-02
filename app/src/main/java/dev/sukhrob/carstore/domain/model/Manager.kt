package dev.sukhrob.carstore.domain.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import dev.sukhrob.carstore.utils.Constants

@IgnoreExtraProperties
data class Manager(
    val managerId: String? = null,
    var fullname: String? = null,
    var email: String? = null,
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            Constants.managerId to managerId,
            Constants.fullname to fullname,
            Constants.email to email,
        )
    }
}