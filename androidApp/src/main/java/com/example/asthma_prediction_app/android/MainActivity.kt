package com.example.asthma_prediction_app.android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import androidx.compose.ui.window.PopupProperties
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SummaryScreenPreview() {
    MyApplicationTheme {
        val navController = rememberNavController()
        val sampleSummary = mapOf(
            "Sex" to "Male",
            "Age Group" to "18–44",
            "Race" to "White, non-Hispanic",
            "BMI" to "Normal"
        )
        SummaryScreen(summary = sampleSummary, navController = navController)
    }
}





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(onStartClick = {
                            navController.navigate("questionnaire")
                        })
                    }
                    composable("questionnaire") {
                        QuestionnaireScreen(navController)
                    }
                    composable("summary/{data}") { backStackEntry ->
                        val dataJson = backStackEntry.arguments?.getString("data")
                        val summaryMap = parseSummaryJson(dataJson ?: "")
                        SummaryScreen(summaryMap, navController)
                    }
                    composable("prediction") {
                        PredictionScreen()
                    }


                }
            }
        }
    }
}


@Composable
fun HomeScreen(onStartClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Asthma Prediction App",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onStartClick) {
                Text("Start Prediction")
            }
        }
    }
}

@Composable
fun QuestionnaireScreen(navController: NavController) {

    val scrollState = rememberScrollState()

    // State variables (same as your current code) ...
    val ageGroups = listOf("18–44", "45–64", "65+")
    var selectedAge by remember { mutableStateOf(ageGroups[0]) }

    val races = listOf("White, non-Hispanic", "Black, non-Hispanic", "Other, non-Hispanic", "Multiracial, non-Hispanic", "Hispanic")
    var selectedRace by remember { mutableStateOf(races[0]) }

    val educationLevels = listOf("High (College or above)", "Low")
    var selectedEdu by remember { mutableStateOf(educationLevels[0]) }

    val incomeLevels = listOf("High", "Middle", "Low")
    var selectedIncome by remember { mutableStateOf(incomeLevels[0]) }

    val bmiCategories = listOf("Underweight", "Normal", "Overweight", "Obese")
    var selectedBMI by remember { mutableStateOf(bmiCategories[1]) }

    var sex by remember { mutableStateOf("Male") }
    var fruit by remember { mutableStateOf("Yes") }
    var veg by remember { mutableStateOf("Yes") }
    var activity by remember { mutableStateOf("Yes") }
    var smoker by remember { mutableStateOf("Current") }

    val electronicCigarettes = listOf("Never Smoke", "Former Smoker", "Current Smoker")
    var selectedelectric by remember { mutableStateOf(electronicCigarettes[1]) }

    var bingeDrinking by remember { mutableStateOf("Yes") }

    val mentalHealthStatus = listOf("No Problem", "Some Problem", "Severe Problem")
    var selectedMentalHealth by remember { mutableStateOf(mentalHealthStatus[1]) }

    val physicalHealthStatus = listOf("No Problem", "Some Problem", "Severe Problem")
    var selectedPhysicalHealth by remember { mutableStateOf(physicalHealthStatus[1]) }

    var medicalCheckup by remember { mutableStateOf("Yes") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Scrollable Form Fields
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(bottom = 80.dp) // leave space for Submit button
            ) {
                Text("Questionnaire", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(24.dp))

                // Form inputs
                Text("Sex")
                Row {
                    listOf("Male", "Female").forEach {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = sex == it, onClick = { sex = it })
                            Text(it)
                        }
                        Spacer(Modifier.width(16.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))
                DropdownField("Age Group", ageGroups, selectedAge) { selectedAge = it }
                DropdownField("Race", races, selectedRace) { selectedRace = it }
                DropdownField("Education Level", educationLevels, selectedEdu) { selectedEdu = it }
                DropdownField("Income Level", incomeLevels, selectedIncome) { selectedIncome = it }
                DropdownField("BMI Category", bmiCategories, selectedBMI) { selectedBMI = it }
                YesNoRadio("Consume Fruit Daily", fruit) { fruit = it }
                YesNoRadio("Consume Vegetables Daily", veg) { veg = it }
                YesNoRadio("Physical Activity in last 30 days", activity) { activity = it }

                Text("Smoking Status")
                Row {
                    listOf("Current", "Former").forEach {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = smoker == it, onClick = { smoker = it })
                            Text(it)
                        }
                        Spacer(Modifier.width(16.dp))
                    }
                }

                DropdownField(
                    "Electronic Cigarette Usage",
                    electronicCigarettes,
                    selectedelectric
                ) { selectedelectric = it }
                YesNoRadio("Binge Drinking in the Past Month", bingeDrinking) { bingeDrinking = it }
                DropdownField(
                    "Mental Health Status (Past 30 Days)",
                    mentalHealthStatus,
                    selectedMentalHealth
                ) { selectedMentalHealth = it }
                DropdownField(
                    "Physical Health Status (Past 30 Days)",
                    physicalHealthStatus,
                    selectedPhysicalHealth
                ) { selectedPhysicalHealth = it }
                YesNoRadio("Had Medical Checkup in Past Year", medicalCheckup) {
                    medicalCheckup = it
                }

                Spacer(Modifier.height(16.dp))
            }

            // Submit Button at Bottom Center
            Button(
                onClick = {
                    val summary = mapOf(
                        "Sex" to sex,
                        "Age Group" to selectedAge,
                        "Race" to selectedRace,
                        "Education" to selectedEdu,
                        "Income" to selectedIncome,
                        "BMI" to selectedBMI,
                        "Consume Fruit" to fruit,
                        "Consume Vegetable" to veg,
                        "Physical Activity" to activity,
                        "Smoking Status" to smoker,
                        "Electronic Cigarette Usage" to selectedelectric,
                        "Binge Drinking" to bingeDrinking,
                        "Mental Health Status" to selectedMentalHealth,
                        "Physical Health Status" to selectedPhysicalHealth,
                        "Medical Checkup" to medicalCheckup
                    )
                    val dataJson = Uri.encode(summary.toJson())
                    navController.navigate("summary/$dataJson")
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            ) {
                Text("Submit")
            }
        }
    }
}


@Composable
fun SummaryScreen(summary: Map<String, String>, navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 80.dp), // more top/bottom padding
            horizontalAlignment = Alignment.CenterHorizontally // center title
        ) {
            Text(
                text = "Summary",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp)) // more space between title and content

            summary.forEach { (label, value) ->
                Text(
                    text = "$label: $value",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(38.dp))

            Button(
                onClick = {
                    navController.navigate("prediction")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Confirm")
            }
        }
    }
}




@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label)
        Box {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }, // open on click
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )

            // 👇 Fix dropdown not showing in scrollable Column
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true) // <== key fix
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun YesNoRadio(label: String, selected: String, onSelect: (String) -> Unit) {
    Text(label)
    Row {
        listOf("Yes", "No").forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = selected == it, onClick = { onSelect(it) })
                Text(it)
            }
            Spacer(Modifier.width(16.dp))
        }
    }
    Spacer(Modifier.height(16.dp))
}

@OptIn(ExperimentalSerializationApi::class)
fun Map<String, String>.toJson(): String {
    return Json.encodeToString(this)
}

fun parseSummaryJson(json: String): Map<String, String> {
    return try {
        Json.decodeFromString<Map<String, String>>(Uri.decode(json))
    } catch (e: Exception) {
        emptyMap()
    }
}


@Composable
fun PredictionScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("This is the Prediction Screen", style = MaterialTheme.typography.headlineMedium)
        }
    }

}



