package com.cy.practice.simplecontact.ui.screen.contact_list

sealed class ContactListEvent {
    data class ToDial(val phone: String) : ContactListEvent()
    data class ToSms(val phone: String) : ContactListEvent()
}