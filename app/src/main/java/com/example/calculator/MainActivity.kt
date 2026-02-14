package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var expression by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = expression, fontSize = 40.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(16.dp))
        val buttons = listOf(
            listOf("(", ")", "DEL", "/"),
            listOf("7","8","9","*"),
            listOf("4","5","6","-"),
            listOf("1","2","3","+"),
            listOf("0",".","=")
        )
        Column {
            buttons.forEach { row -> Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                row.forEach { button -> CalculatorButton(button) {
                    when (button) {
                        "C" -> expression = ""
                        "=" -> {
                            try {
                                val result = ExpressionBuilder(expression).build().evaluate()
                                expression = result.toString()
                            }
                            catch (e: Exception) {
                                expression = "Error"
                            }
                        }
                        "DEL" -> {
                            if (expression == "Error") {
                                expression = ""
                            } else if (expression.isNotEmpty()) {
                                expression = expression.dropLast(1)
                            }
                        }
                        else -> expression += button
                    }
                }
                }
            } }
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(onClick=onClick, modifier= Modifier.padding(8.dp).size(70.dp), shape = CircleShape) {

        if (text == "DEL") {
            Icon(
                imageVector = Icons.Default.Backspace,
                contentDescription = "Delete"
            )
        } else {
            Text(text = text, fontSize = 24.sp)
        }
    }
}