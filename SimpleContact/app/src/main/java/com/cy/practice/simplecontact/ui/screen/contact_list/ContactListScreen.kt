package com.cy.practice.simplecontact.ui.screen.contact_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cy.practice.simplecontact.domain.model.Contact
import com.cy.practice.simplecontact.ui.screen.contact_list.component.ContactBottomSheet
import com.cy.practice.simplecontact.ui.screen.contact_list.component.ContactList
import com.cy.practice.simplecontact.ui.screen.contact_list.component.SearchBar
import com.cy.practice.simplecontact.util.toDial
import com.cy.practice.simplecontact.util.toSms
import timber.log.Timber


@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    vm: ContactListViewModel = viewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        vm.event.collect { event ->
            when (event) {
                is ContactListEvent.ToDial -> context.toDial(event.phone)
                is ContactListEvent.ToSms -> context.toSms(event.phone)
            }
        }
    }

    ContactListScreen(state, vm::onAction, modifier)
}


@OptIn(ExperimentalMaterial3Api::class)
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

            ContactList(
                state.filteredContacts,
                { onAction(ContactListAction.ViewContact(it)) },
            )
        }
    }

    if (state.viewContact != null) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ContactBottomSheet(
            state.viewContact,
            { onAction(ContactListAction.ViewContact(null)) },
            { onAction(ContactListAction.ToDial(it)) },
            { onAction(ContactListAction.ToSms(it)) },
            sheetState = sheetState,
        )
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

