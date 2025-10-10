package br.com.naitzel.hertz.di

import android.content.Context
import br.com.naitzel.hertz.data.local.dao.FinancialDao
import br.com.naitzel.hertz.data.local.dao.ServiceOrderDao
import br.com.naitzel.hertz.data.local.database.HertzDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): HertzDatabase {
        return HertzDatabase.getDatabase(context)
    }

    @Provides
    fun provideServiceOrderDao(db: HertzDatabase): ServiceOrderDao {
        return db.budgetDao()
    }

    @Provides
    fun provideFinancialDao(db: HertzDatabase): FinancialDao {
        return db.financialDao()
    }
}
