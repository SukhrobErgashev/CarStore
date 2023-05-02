package dev.sukhrob.carstore.data.datastore

import kotlinx.coroutines.flow.Flow

interface UiSource {

    val justInstalled: Flow<Boolean>

    suspend fun setJustInstalled(b: Boolean)

}