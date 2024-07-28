package io.github.kishigaki.calculatorapp.others.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kishigaki.calculatorapp.application.calculator.Key


@Composable
fun KeyPad(onClick: (Key) -> Unit) {
    Row {
        // 1st..3rd column
        Column(Modifier.weight(3.0F)) {
            Row(Modifier.weight(1.0F)) {
                for (key in listOf(Key.ALL_CLEAR, Key.DIVIDE, Key.TIMES)) {
                    Box(Modifier.weight(1.0F)) {
                        Key(onClick, key)
                    }
                }
            }
            Row(Modifier.weight(1.0F)) {
                for (key in listOf(Key.SEVEN, Key.EIGHT, Key.NINE)) {
                    Box(Modifier.weight(1.0F)) {
                        Key(onClick, key)
                    }
                }
            }
            Row(Modifier.weight(1.0F)) {
                for (key in listOf(Key.FOUR, Key.FIVE, Key.SIX)) {
                    Box(Modifier.weight(1.0F)) {
                        Key(onClick, key)
                    }
                }
            }
            Row(Modifier.weight(1.0F)) {
                for (key in listOf(Key.ONE, Key.TWO, Key.THREE)) {
                    Box(Modifier.weight(1.0F)) {
                        Key(onClick, key)
                    }
                }
            }
            Row(Modifier.weight(1.0F)) {
                Box(Modifier.weight(2.0F)) {
                    Key(onClick, Key.ZERO)
                }
                Box(Modifier.weight(1.0F)) {
                    Key(onClick, Key.POINT)
                }
            }
        }
        // final(4th) column
        Column(Modifier.weight(1.0F)) {
            for (key in listOf(Key.DELETE, Key.MINUS, Key.PLUS)) {
                Box(Modifier.weight(1.0F)) {
                    Key(onClick, key)
                }
            }
            Box(Modifier.weight(2.0F)) {
                Key(onClick, Key.EQUAL)
            }
        }
    }
}

@Composable
fun Key(onClick: (Key) -> Unit, key: Key) {
    Box(Modifier.padding(5.dp).fillMaxSize()) {
        FilledTonalButton({ onClick(key) }, Modifier.fillMaxSize()) { Text(key.text, fontSize = 30.sp) }
    }
}
