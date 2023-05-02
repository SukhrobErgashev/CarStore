package dev.sukhrob.carstore.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.carstore.data.datastore.UiSource
import dev.sukhrob.carstore.data.datastore.UiSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiSourceModule {

    @Provides
    @Singleton
    fun provideUserPref(@ApplicationContext context: Context): UiSource =
        UiSourceImpl(context)
}