package com.cy.practice.simplecontact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.cy.practice.simplecontact.ui.theme.SimpleContactTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleContactTheme {
                Scaffold { innerPadding ->
                    Text("Hello World", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
