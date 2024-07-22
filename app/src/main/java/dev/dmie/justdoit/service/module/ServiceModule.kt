package dev.dmie.justdoit.service.module

import dev.dmie.justdoit.service.AuthenticationService
import dev.dmie.justdoit.service.StorageService
import dev.dmie.justdoit.service.impl.AuthenticationServiceImpl
import dev.dmie.justdoit.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.impl.LogServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAuthenticationService(impl: AuthenticationServiceImpl): AuthenticationService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideLogServiceImpl(impl: LogServiceImpl): LogService
}
