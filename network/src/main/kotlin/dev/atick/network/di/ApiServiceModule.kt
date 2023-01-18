package dev.atick.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.atick.network.api.HemaCareApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        RetrofitModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Singleton
    @Provides
    fun provideJetpackApi(retrofit: Retrofit): HemaCareApi {
        return retrofit.create(HemaCareApi::class.java)
    }
}