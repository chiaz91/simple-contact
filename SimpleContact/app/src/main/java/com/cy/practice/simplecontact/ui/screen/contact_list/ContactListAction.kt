package com.cy.practice.simplecontact.ui.screen.contact_list

sealed class ContactListAction {
    data class SearchContacts(val query: String) : ContactListAction()
}