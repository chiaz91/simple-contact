package com.cy.practice.simplecontact.ui.screen.contact_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    vm: ContactListViewModel = viewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    ContactListScreen(state, modifier)
}


@Composable
fun ContactListScreen(
    state: ContactListState,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier.fillMaxWidth(),
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimatedVisibility(visible = state.isLoading) {
                LinearProgressIndicator(
                    Modifier.fillMaxWidth()
                )
            }

            LazyColumn {
                items(state.contacts) {
                    Text(it.name)
                }
            }
        }
    }
}


@Preview
@Composable
private fun ContactListScreenPreview() {
    MaterialTheme {
        ContactListScreen()
    }
}

