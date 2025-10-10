package br.com.naitzel.hertz.di

import android.content.Context
import android.content.SharedPreferences
import br.com.naitzel.hertz.core.manager.ConfigurationManager
import br.com.naitzel.hertz.core.manager.CrashedManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSessionManager(context: Context): ConfigurationManager {
        return ConfigurationManager(context)
    }

    @Provides
    @Singleton
    fun provideCrashedManager(context: Context): CrashedManager {
        return CrashedManager(context)
    }
}