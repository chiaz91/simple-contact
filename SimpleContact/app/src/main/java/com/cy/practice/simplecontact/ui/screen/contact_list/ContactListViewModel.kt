package com.cy.practice.simplecontact.ui.screen.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cy.practice.simplecontact.domain.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactListState())
    val uiState = _uiState
        .onStart { loadContacts() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000L),
            ContactListState()
        )



    private fun loadContacts() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.Default) {
            val contacts = contactRepository.getContacts()
            _uiState.update {
                it.copy(
                    contacts = contacts,
                    isLoading = false
                )
            }
        }
    }

}