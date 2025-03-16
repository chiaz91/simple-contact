package com.cy.practice.simplecontact.ui.screen.contact_list.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cy.practice.simplecontact.domain.model.Contact


@Composable
fun ContactListHeader(header: String, modifier: Modifier = Modifier) {
    Text(
        text = header,
        style = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        ),
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp, 8.dp)
    )
}

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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactList(
    contacts: Map<Char, List<Contact>>,
    onContactClicked: (Contact) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(state = listState, modifier = modifier) {
        contacts.map { entry ->
            stickyHeader {
                ContactListHeader(entry.key.toString(), Modifier.animateItem())
            }
            items(
                entry.value, key = { it.id }
            ) { contact ->
                ContactListItem(
                    contact = contact,
                    Modifier.animateItem().clickable { onContactClicked(contact) }
                )
            }
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
    MaterialTheme {
        ContactList(groupedContacts, {})
    }
}
