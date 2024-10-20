package com.example.bmi_calculator_with_viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val bmiViewModel: BMIViewModel = viewModel()
                BMICalculatorApp(bmiViewModel)
            }
        }
    }
}

@Composable
fun BMICalculatorApp(viewModel: BMIViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("BMI Calculator", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Height Input
            OutlinedTextField(
                value = viewModel.height.value?.toString() ?: "", // Safe call for height
                onValueChange = { newHeight ->
                    // Update height only if the input is a valid float or empty (to clear input)
                    if (newHeight.isEmpty() || newHeight.toFloatOrNull() != null) {
                        viewModel.updateHeight(newHeight.toFloatOrNull() ?: 0f)
                    }
                },
                label = { Text("Height (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Weight Input
            OutlinedTextField(
                value = viewModel.weight.value?.toString() ?: "", // Safe call for weight
                onValueChange = { newWeight ->
                    // Update weight only if the input is a valid float or empty (to clear input)
                    if (newWeight.isEmpty() || newWeight.toFloatOrNull() != null) {
                        viewModel.updateWeight(newWeight.toFloatOrNull() ?: 0f)
                    }
                },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Observing the BMI result safely
            val bmiResult by viewModel.bmiResult.observeAsState("")

            // Displaying the result
            Text(
                text = if (bmiResult.isNotEmpty()) "Your BMI: $bmiResult" else "",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        val bmiViewModel: BMIViewModel = BMIViewModel() // Mocked ViewModel for Preview
        BMICalculatorApp(viewModel = bmiViewModel)
    }
}
