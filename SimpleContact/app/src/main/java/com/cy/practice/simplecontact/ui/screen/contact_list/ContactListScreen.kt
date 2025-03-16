package com.cy.practice.simplecontact.ui.screen.contact_list

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ContactListScreen(modifier: Modifier = Modifier, vm: ContactListViewModel = viewModel()) {
    Text("Contact List Screen")
}


@Preview
@Composable
private fun ContactListScreenPreview() {
    MaterialTheme {
        ContactListScreen()
    }
}

