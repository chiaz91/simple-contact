package com.cy.practice.simplecontact.ui.screen.contact_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cy.practice.simplecontact.domain.model.Contact
import com.cy.practice.simplecontact.ui.screen.contact_list.component.ContactList
import com.cy.practice.simplecontact.ui.screen.contact_list.component.SearchBar


@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    vm: ContactListViewModel = viewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    ContactListScreen(state, vm::onAction, modifier)
}


@Composable
fun ContactListScreen(
    state: ContactListState,
    onAction: (ContactListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxWidth(),
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(
                query = state.searchQuery,
                onQueryChanged = { query -> onAction(ContactListAction.SearchContacts(query)) },
                onClear = { onAction(ContactListAction.SearchContacts("")) },
            )
            AnimatedVisibility(visible = state.isLoading) {
                LinearProgressIndicator(
                    Modifier.fillMaxWidth()
                )
            }

            ContactList(state.filteredContacts)
        }
    }
}


@Preview
@Composable
private fun ContactListScreenPreview() {
    val groupedContacts = mapOf(
        'A' to listOf(
            Contact("1", "Alice"),
            Contact("2", "Adam"),
        ),
        'B' to listOf(
            Contact("3", "Bella"),
            Contact("4", "Blake"),
        ),
        'c' to listOf(
            Contact("5", "Chris"),
            Contact("6", "Clara"),
        )
    )
    val state = ContactListState(
        isLoading = true,
        filteredContacts = groupedContacts
    )
    MaterialTheme {
        ContactListScreen(state,{})
    }
}

