package com.cy.practice.simplecontact.data

import com.cy.practice.simplecontact.domain.model.Contact
import com.cy.practice.simplecontact.domain.repository.ContactRepository
import kotlinx.coroutines.delay
import javax.inject.Inject


class ContactsRepositoryImpl @Inject constructor(
    private val dataSource: DeviceContactsSource
) : ContactRepository {

    override suspend fun getContacts(): List<Contact> {
        delay(2000L) // simulate long processing
        return dataSource.getContacts()
    }

}