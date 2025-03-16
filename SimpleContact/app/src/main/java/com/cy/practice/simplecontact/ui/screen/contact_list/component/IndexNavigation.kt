package com.cy.practice.simplecontact.ui.screen.contact_list.component

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AdaptiveIndexNavigation(
    indexList: List<Char>,
    onIndexSelected: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape && indexList.size > 15) {
        val mid = indexList.size / 2
        val leftList = indexList.take(mid)
        val rightList = indexList.drop(mid)

        Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            IndexNavigation(leftList, onIndexSelected)
            IndexNavigation(rightList, onIndexSelected)
        }
    } else {
        IndexNavigation(indexList, onIndexSelected, modifier)
    }
}



@Composable
fun IndexNavigation(
    indexList: List<Char>,
    onIndexSelected: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    if (indexList.isEmpty()) return

    val textSize = with(LocalDensity.current) { 14.sp.toPx() }
    val textMeasurer = rememberTextMeasurer()
    val textColor = MaterialTheme.colorScheme.onSurface
    val highlightColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)

    var selectedLetter by remember { mutableStateOf<Char?>(null) }

    BoxWithConstraints(modifier) {
        val availableHeight = constraints.maxHeight.toFloat()
        val contentHeight = (indexList.size * textSize * 1.5f).coerceAtMost(availableHeight)
        val letterSpacing = contentHeight / indexList.size
        val cornerRadius = letterSpacing / 2 // Match letter highlight

        Canvas(
            modifier = Modifier
                .height(with(LocalDensity.current) { contentHeight.toDp() }) // Wrap content height
                .width(30.dp)
                .pointerInput(indexList) {
                    detectTapGestures(
                        onPress = { offset ->
                            selectedLetter = findSelectedLetter(offset.y, indexList, contentHeight)
                            selectedLetter?.let(onIndexSelected)
                            tryAwaitRelease()
                            selectedLetter = null
                        }
                    )
                }
                .pointerInput(indexList) {
                    detectVerticalDragGestures(
                        onDragStart = { offset ->
                            selectedLetter = findSelectedLetter(offset.y, indexList, contentHeight)
                            selectedLetter?.let(onIndexSelected)
                        },
                        onVerticalDrag = { change, _ ->
                            change.consume()
                            val letter =
                                findSelectedLetter(change.position.y, indexList, contentHeight)
                            if (letter != null && letter != selectedLetter) {
                                selectedLetter = letter
                                onIndexSelected(letter)
                            }
                        },
                        onDragEnd = {
                            selectedLetter = null
                        }
                    )
                }
        ) {
            indexList.forEachIndexed { index, letter ->
                val yPos = (index + 0.5f) * letterSpacing
                val textLayoutResult = textMeasurer.measure(
                    text = letter.toString(),
                    style = TextStyle(fontSize = 14.sp)
                )

                if (letter == selectedLetter) {
                    drawCircle(
                        color = highlightColor,
                        radius = cornerRadius,
                        center = Offset(size.width / 2, yPos)
                    )
                }
                drawText(
                    textMeasurer = textMeasurer,
                    text = letter.toString(),
                    topLeft = Offset(
                        x = (size.width - textLayoutResult.size.width) / 2,
                        y = yPos - textLayoutResult.size.height / 2
                    ),
                    style = TextStyle(
                        color = textColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

private fun findSelectedLetter(yPosition: Float, indexList: List<Char>, contentHeight: Float): Char? {
    val index = ((yPosition / contentHeight) * indexList.size).toInt().coerceIn(0, indexList.size - 1)
    return indexList.getOrNull(index)
}