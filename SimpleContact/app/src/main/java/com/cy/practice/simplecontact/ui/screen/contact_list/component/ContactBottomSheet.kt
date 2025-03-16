package com.cy.practice.simplecontact.ui.screen.contact_list.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cy.practice.simplecontact.domain.model.Contact


@Composable
fun PhoneNumberItem(
    phone: String,
    onCallClicked: (String) -> Unit,
    onSmsClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(phone, fontSize = 18.sp)
        Spacer(Modifier.weight(1f))
        Row {
            IconButton(onClick = { onCallClicked(phone) }) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { onSmsClicked(phone) }) {
                Icon(
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = "SMS",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}


@Composable
fun ContactDetail(
    contact: Contact,
    onCallClicked: (String) -> Unit,
    onSmsClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp, 16.dp)
            .fillMaxWidth()
    ) {

        Avatar(
            contact.name,
            contact.photoThumbnailUrl,
            size = 80.dp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
        )

        Text(
            text = contact.name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        if (contact.phoneNumbers.isNotEmpty()) {
            LazyColumn {
                items(contact.phoneNumbers) { phone ->
                    PhoneNumberItem(phone, onCallClicked, onSmsClicked)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBottomSheet(
    contact: Contact,
    onDismissRequest: () -> Unit,
    onCallClicked: (String) -> Unit,
    onSmsClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = {},
        modifier = modifier
    ) {
        ContactDetail(
            contact,
            onCallClicked, onSmsClicked
        )
    }
}







@Preview(showBackground = true)
@Composable
private fun PhoneNumberListPreview() {
    val phone = "1234 5678"
    MaterialTheme {
        PhoneNumberItem(phone, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactDetailPreview() {
    val contact = Contact(
        id = "1",
        name = "Alice",
        phoneNumbers = listOf(
            "1234 5678",
            "8765 4321"
        ),
    )
    MaterialTheme {
        ContactDetail(contact, {}, {})
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ContactBottomSheetPreview() {
    val contact = Contact(
        id = "1",
        name = "Alice",
        phoneNumbers = listOf(
            "1234 5678",
            "8765 4321"
        ),
    )
    val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
    MaterialTheme {
        ContactBottomSheet(contact, {}, {}, {}, sheetState = sheetState)
    }
}
