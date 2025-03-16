package com.cy.practice.simplecontact.ui.screen.contact_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cy.practice.simplecontact.domain.model.Contact


@Composable
fun ContactListItem(contact: Contact, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp, 4.dp)
    ) {
        Avatar(contact.name, contact.photoThumbnailUrl, size = 48.dp)
        Spacer(Modifier.width(8.dp))

        Text(
            text = contact.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}


@Composable
fun ContactList(
    contacts: List<Contact>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(state = listState, modifier = modifier) {
        items(contacts, key = { it.id }) { contact ->
            ContactListItem(contact = contact, modifier = Modifier.animateItem())
        }
    }
}








// Previews
@Preview
@Composable
private fun ContactListPreview(modifier: Modifier = Modifier) {
    val contact = Contact("1", "Alice")
    MaterialTheme {
        ContactListItem(contact)
    }
}


@Preview
@Composable
private fun ContactListScreenPreview(modifier: Modifier = Modifier) {
    val contacts = listOf(
        Contact("1", "Alice"),
        Contact("2", "Adam"),
    )
    MaterialTheme {
        ContactList(contacts)
    }
}
