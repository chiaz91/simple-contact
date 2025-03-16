package com.cy.practice.simplecontact.domain.repository

import com.cy.practice.simplecontact.domain.model.Contact


interface ContactRepository {
    suspend fun getContacts(): List<Contact>
}