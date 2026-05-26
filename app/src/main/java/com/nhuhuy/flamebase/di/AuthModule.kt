package com.nhuhuy.flamebase.di

import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.data.repository.AuthRepository
import com.nhuhuy.flamebase.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideFlameAuth(): FlameAuth = FlameAuth
    }
}
