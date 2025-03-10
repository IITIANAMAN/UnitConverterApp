package com.example.unitconvertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconvertor.ui.theme.UnitConvertorTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConvertorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConvertor()
                }
            }
        }
    }
}

@Composable
fun UnitConvertor() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var InputUnit by remember { mutableStateOf("Meters") }
    var OutputUnit by remember { mutableStateOf("Meters") }
    var IExpanded by remember { mutableStateOf(false) }
    var OExpanded by remember { mutableStateOf(false) }

    // Conversion Factors for Different Units
    val conversionFactors = mapOf(
        "Meters" to 1.0,
        "Kilometers" to 0.001,
        "Centimeters" to 100.0,
        "Millimeters" to 1000.0,
        "Miles" to 0.000621371,
        "Yards" to 1.09361,
        "Feet" to 3.28084,
        "Inches" to 39.3701
    )

    // Function to Perform Conversion
    fun conversionUnit() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val inputFactor = conversionFactors[InputUnit] ?: 1.0
        val outputFactor = conversionFactors[OutputUnit] ?: 1.0

        val result = (inputValueDouble / inputFactor * outputFactor * 100.0).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Unit Converter", style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace), color = Color.Magenta )
        Spacer(modifier = Modifier.height(15.dp))

        // Input Field
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                conversionUnit() // Auto-update output on input change
            },
            label = { Text(text = "Enter a value") }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row {
            // Input Unit Selector
            Box {
                Button(onClick = { IExpanded = true }) {
                    Text(text = InputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = IExpanded, onDismissRequest = { IExpanded = false }) {
                    conversionFactors.keys.forEach { unit ->
                        DropdownMenuItem(text = { Text(text = unit) }, onClick = {
                            InputUnit = unit
                            IExpanded = false
                            conversionUnit() // Update conversion when unit changes
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Output Unit Selector
            Box {
                Button(onClick = { OExpanded = true }) {
                    Text(text = OutputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = OExpanded, onDismissRequest = { OExpanded = false }) {
                    conversionFactors.keys.forEach { unit ->
                        DropdownMenuItem(text = { Text(text = unit) }, onClick = {
                            OutputUnit = unit
                            OExpanded = false
                            conversionUnit() // Update conversion when unit changes
                        })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Display Result
        Text(
            text = "Result: $outputValue",
            style = MaterialTheme.typography.displaySmall.copy(fontFamily = FontFamily.Monospace),
            color = Color.Red
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConvertorPreview() {
    UnitConvertor()
}
