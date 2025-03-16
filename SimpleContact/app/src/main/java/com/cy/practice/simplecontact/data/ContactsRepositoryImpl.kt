package com.cy.practice.simplecontact.data

import com.cy.practice.simplecontact.domain.model.Contact
import com.cy.practice.simplecontact.domain.repository.ContactRepository
import javax.inject.Inject


class ContactsRepositoryImpl @Inject constructor(
    private val dataSource: DeviceContactsSource
) : ContactRepository {

    override suspend fun getContacts(): List<Contact> {
        return dataSource.getContacts()
    }

}