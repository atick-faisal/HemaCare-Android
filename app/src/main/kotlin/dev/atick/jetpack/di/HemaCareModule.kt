package dev.atick.jetpack.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.atick.jetpack.repository.HemaCareRepository
import dev.atick.jetpack.repository.HemaCareRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HemaCareModule {

    @Binds
    @Singleton
    abstract fun bindHemaCareRepository(
        hemaCareRepositoryImpl: HemaCareRepositoryImpl
    ): HemaCareRepository

}