package dev.atick.jetpack.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ExecutorModule {

    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    private const val KEEP_ALIVE_TIME = 1L
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    private val workQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()


    @Provides
    @Singleton
    fun provideExecutor() = ThreadPoolExecutor(
        NUMBER_OF_CORES,
        NUMBER_OF_CORES,
        KEEP_ALIVE_TIME,
        KEEP_ALIVE_TIME_UNIT,
        workQueue
    )

}