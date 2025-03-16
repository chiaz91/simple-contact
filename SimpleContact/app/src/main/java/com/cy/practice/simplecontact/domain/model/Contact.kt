package com.cy.practice.simplecontact.domain.model

// typealias GroupedContacts = Map<Char, List<Contact>>

data class Contact(
    val id: String = "",
    val name: String = "",
    val photoUri: String? = null,
    val photoThumbnailUrl: String? = null,
    val phoneNumbers: List<String> = emptyList(),
) {
    val initial = name.trim().firstOrNull()

    fun isMatch(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
                || phoneNumbers.any { phone -> phone.contains(query)}

    }
}