package com.cy.practice.simplecontact.ui.screen.contact_list

import com.cy.practice.simplecontact.domain.model.Contact


data class ContactListState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val contacts: Map<Char, List<Contact>> = emptyMap(),
    val filteredContacts: Map<Char, List<Contact>> = emptyMap(),
    val viewContact: Contact? = null,
) {
    fun getIndexPosition(letter: Char): Int {
        if (!filteredContacts.keys.contains(letter)) return -1
        val keyIndex = filteredContacts.keys.indexOf(letter)
        val firstChild = filteredContacts[letter]!!.first()
        val childOffset = filteredContacts.values
            .flatten()
            .indexOfFirst {
                it == firstChild
            }

        return keyIndex + childOffset
    }
}