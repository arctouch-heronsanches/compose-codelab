package com.arctouch.heronsanches.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arctouch.heronsanches.composetutorial.ui.theme.ComposeTutorialTheme

class CodeLabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTutorialTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (shouldShowOnboarding) OnboardingScreen { shouldShowOnboarding = false }
        else Greetings()
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("Android", "Compose", "Kotlin")
) {
    Surface(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            names.forEachIndexed { index, name ->
                /* TODO: understand it:
                    "Composable functions can execute frequently and in any order,
                        you must not rely on the ordering in which the code is executed,
                        or on how many times this function will be recomposed."
                */
                Greeting(name = name, modifier = Modifier.fillMaxWidth())
                val isLastElement = index == names.size - 1
                if (!isLastElement) Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            Spacer(modifier = Modifier.width(4.dp))

            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, onContinueClicked: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Basics Codelab!")

        Button(
            onClick = { onContinueClicked() },
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            Text(text = "Continue")
        }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingScreenPreview() {
    ComposeTutorialTheme {
        OnboardingScreen {}
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTutorialTheme {
        Greeting(name = "Android")
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview() {
    ComposeTutorialTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}
