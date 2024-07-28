package io.github.kishigaki.calculatorapp.others.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Screen(expressionText: String, cursorPosition: Int, resultText: String, onClick: (Int) -> Unit) {
    Column(Modifier.fillMaxWidth().padding(5.dp), horizontalAlignment = Alignment.End) {
        HorizontalDivider()
        Row(Modifier.height(100.dp).horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.CenterVertically) {
            if (cursorPosition == 0) {
                TextCaret()
            }
            expressionText.forEachIndexed { index, c ->
                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterEnd) {
                    Row(Modifier.matchParentSize()) {
                        Box(Modifier.fillMaxHeight().weight(1.0F).pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    awaitPointerEvent()
                                    onClick(index - 1)
                                }
                            }
                        })
                        Box(Modifier.fillMaxHeight().weight(1.0F).pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    awaitPointerEvent()
                                    onClick(index)
                                }
                            }
                        })
                    }
                    Text(c.toString(), fontSize = 50.sp, color = MaterialTheme.colorScheme.onPrimary)
                    if (index == cursorPosition - 1) {
                        TextCaret()
                    }
                }
            }
        }
        HorizontalDivider()
        Row(Modifier.height(50.dp).horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.CenterVertically) {
            Text(resultText, fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary)
        }
        HorizontalDivider()
        Text(cursorPosition.toString())
    }
}

@Composable
fun TextCaret() {
    val isVisible = remember { mutableStateOf(true) }
    if (isVisible.value) {
        VerticalDivider(Modifier.padding(vertical = 10.dp))
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            isVisible.value = !isVisible.value
        }
    }
}