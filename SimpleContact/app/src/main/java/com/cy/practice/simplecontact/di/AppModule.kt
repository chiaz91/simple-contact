package com.cy.practice.simplecontact.di

import android.content.Context
import com.cy.practice.simplecontact.data.ContactsRepositoryImpl
import com.cy.practice.simplecontact.data.DeviceContactsSource
import com.cy.practice.simplecontact.domain.repository.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideContactsSource(@ApplicationContext context: Context) : DeviceContactsSource {
        return DeviceContactsSource(context)
    }

    @Provides
    @Singleton
    fun provideContactRepository(contactSource: DeviceContactsSource) : ContactRepository {
        return ContactsRepositoryImpl(contactSource)
    }
}