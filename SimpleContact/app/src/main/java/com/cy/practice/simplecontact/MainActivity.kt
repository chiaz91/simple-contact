package com.cy.practice.simplecontact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.cy.practice.simplecontact.ui.screen.contact_list.ContactListScreen
import com.cy.practice.simplecontact.ui.screen.contact_permission.ContactsPermissionScreen
import com.cy.practice.simplecontact.ui.theme.SimpleContactTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleContactTheme {
                ContactApp()
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactApp() {
    val permissionState = rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    if (permissionState.status.isGranted) {
        ContactListScreen()
    } else {
        ContactsPermissionScreen(permissionState = permissionState)
    }
}
