package com.cy.practice.simplecontact.ui.screen.contact_list

import com.cy.practice.simplecontact.domain.model.Contact


data class ContactListState(
    val isLoading: Boolean = false,
    val contacts: List<Contact> = emptyList(),
) {
}