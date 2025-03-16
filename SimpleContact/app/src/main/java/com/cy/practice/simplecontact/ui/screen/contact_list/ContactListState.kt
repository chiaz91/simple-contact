package com.cy.practice.simplecontact.ui.screen.contact_list

import com.cy.practice.simplecontact.domain.model.Contact


data class ContactListState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val contacts: Map<Char, List<Contact>> = emptyMap(),
    val filteredContacts: Map<Char, List<Contact>> = emptyMap(),
    val viewContact: Contact? = null,
)