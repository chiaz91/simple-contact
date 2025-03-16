package com.cy.practice.simplecontact.ui.screen.contact_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.cy.practice.simplecontact.ui.theme.blue
import com.cy.practice.simplecontact.ui.theme.coral
import com.cy.practice.simplecontact.ui.theme.green
import com.cy.practice.simplecontact.ui.theme.lavender
import com.cy.practice.simplecontact.ui.theme.peach
import kotlin.math.absoluteValue


@Composable
fun Avatar(
    name: String,
    photoUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    colors: List<Color> = listOf(blue, green, lavender, peach, coral)
) {
    // pick a color based on name's hashcode
    val colorIndex = name.hashCode().absoluteValue % colors.size
    val color = colors[colorIndex]

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
            .border(2.dp, Color.White, CircleShape)
            .padding(2.dp)
    ) {
        SubcomposeAsyncImage(
            model = photoUrl,
            contentDescription = "$name's Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = { InitialsText(name, size) },
            error = { InitialsText(name, size) }
        )
    }
}

@Composable
private fun InitialsText(name: String, size: Dp) {
    val initials = name.take(1).uppercase()
    Text(
        text = initials,
        style = TextStyle(
            color = Color.White,
            fontSize = (size.value / 3).sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}


@Preview
@Composable
fun AvatarPreview() {
    MaterialTheme {
        Avatar("Alice", "https://avatar.iran.liara.run/public")
    }
}