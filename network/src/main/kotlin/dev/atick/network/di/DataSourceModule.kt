package dev.atick.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.atick.network.datasource.JetpackDataSource
import dev.atick.network.datasource.JetpackDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindJetpackDataSource(
        jetpackDataSourceImpl: JetpackDataSourceImpl
    ): JetpackDataSource

}