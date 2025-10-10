package br.com.naitzel.hertz.di

import br.com.naitzel.hertz.data.local.dao.FinancialDao
import br.com.naitzel.hertz.data.local.dao.ServiceOrderDao
import br.com.naitzel.hertz.data.repository.FinancialRepository
import br.com.naitzel.hertz.data.repository.ServiceOrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBudgetRepository(dao: ServiceOrderDao): ServiceOrderRepository {
        return ServiceOrderRepository(dao)
    }

    @Provides
    @Singleton
    fun provideFinancialRepository(dao: FinancialDao): FinancialRepository {
        return FinancialRepository(dao)
    }
}