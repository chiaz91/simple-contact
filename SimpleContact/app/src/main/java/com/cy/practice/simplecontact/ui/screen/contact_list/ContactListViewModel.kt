package com.cy.practice.simplecontact.ui.screen.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cy.practice.simplecontact.domain.model.Contact
import com.cy.practice.simplecontact.domain.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEBOUNCE_DELAY = 500L

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {
    private var searchJob: Job? = null

    private val _uiState = MutableStateFlow(ContactListState())
    val uiState = _uiState
        .onStart { loadContacts() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000L),
            ContactListState()
        )

    fun onAction(action: ContactListAction) {
        when (action) {
            is ContactListAction.SearchContacts -> handleSearchContacts(action.query)
        }
    }

    private fun handleSearchContacts(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        // debounce search
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            val filteredContacts = filterContacts(query)
            _uiState.update { it.copy(filteredContacts = filteredContacts) }
        }
    }


    private fun loadContacts() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.Default) {
            val contacts = groupContacts(contactRepository.getContacts())
            _uiState.update {
                it.copy(
                    contacts = contacts,
                    filteredContacts = contacts,
                    isLoading = false
                )
            }
        }
    }


    private fun groupContacts(contacts: List<Contact>): Map<Char, List<Contact>> {
        return contacts
            .groupBy { contact ->
                val firstChar = contact.initial?.uppercaseChar() ?: '#'
                if (firstChar in 'A'..'Z') firstChar else '#'
            }
            .toSortedMap(compareBy<Char> { it == '#' }.thenBy { it })
    }

    private fun filterContacts(query: String): Map<Char, List<Contact>> {
        val allContacts = _uiState.value.contacts
        return if (query.isBlank()) {
            allContacts
        } else {
            allContacts.mapValues { (_, contacts) ->
                contacts.filter { it.isMatch(query) }
            }.filterValues {
                it.isNotEmpty()
            }
        }
    }

}