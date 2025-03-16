package com.cy.practice.simplecontact.ui.screen.contact_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// TODO: improve with pointerInput to allow scroll to navigate
@Composable
fun IndexNavigation(
    indexList: List<Char>,
    onIndexSelected: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(end = 8.dp)
            .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))

    ) {
        indexList.forEach { letter ->
            Text(
                text = letter.toString(),
                modifier = Modifier
                    .clickable { onIndexSelected(letter) }
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 4.dp),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}