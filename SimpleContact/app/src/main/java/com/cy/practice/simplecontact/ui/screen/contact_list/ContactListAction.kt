package com.cy.practice.simplecontact.ui.screen.contact_list

import com.cy.practice.simplecontact.domain.model.Contact

sealed class ContactListAction {
    data class SearchContacts(val query: String) : ContactListAction()
    data class ViewContact(val contact: Contact? = null) : ContactListAction()
    data class ToDial(val phone: String) : ContactListAction()
    data class ToSms(val phone: String) : ContactListAction()
}