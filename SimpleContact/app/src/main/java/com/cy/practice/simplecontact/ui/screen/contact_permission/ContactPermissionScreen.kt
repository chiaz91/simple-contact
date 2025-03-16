package com.cy.practice.simplecontact.ui.screen.contact_permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cy.practice.simplecontact.util.toSetting
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactsPermissionScreen(
    modifier: Modifier = Modifier,
    permissionState: PermissionState = rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Text(
            text = when {
                permissionState.status.isGranted -> "Permission Granted! You can access contacts."
                !permissionState.status.shouldShowRationale -> "Contact permission is required to access contacts."
                else -> "Permission Denied! Go to settings to enable it."
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!permissionState.status.shouldShowRationale) {
            Button(
                onClick = { permissionState.launchPermissionRequest() }
            ) {
                Text("Request Permission")
            }
        } else {
            Button(onClick = {context.toSetting()}) {
                Text("Go App setting")
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun ContactsPermissionScreenPreview() {
    ContactsPermissionScreen()
}
