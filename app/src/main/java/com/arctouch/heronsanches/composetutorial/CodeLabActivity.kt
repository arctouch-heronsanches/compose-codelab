package com.arctouch.heronsanches.composetutorial

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (shouldShowOnboarding) OnboardingScreen { shouldShowOnboarding = false }
        else Greetings()
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    Surface(modifier = modifier) {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            itemsIndexed(items = names) { idx, name ->
                /* TODO: understand it:
                    "Composable functions can execute frequently and in any order,
                        you must not rely on the ordering in which the code is executed,
                        or on how many times this function will be recomposed."
                */
                Greeting(name = name, modifier = Modifier.fillMaxWidth())
                val isLastElement = idx == names.size - 1
                if (!isLastElement) Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }


    Surface(
        color = MaterialTheme.colorScheme.primary,

        modifier = modifier.shadow(
            elevation = 2.dp,
            shape = CircleShape.copy(all = CornerSize(8.dp))
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)

                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            // TODO: .padding(12.dp) - workaround to align icon at top right with "Hello"
            Column(modifier = Modifier
                .weight(1f)
                .padding(12.dp)) {
                Text(text = "Hello,")

                Text(
                    text = name,

                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )

                if (expanded) Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4)
                )
            }

            IconButton(onClick = { expanded = !expanded }) {
                val contentDescription =
                    if (expanded) stringResource(id = R.string.show_less)
                    else stringResource(id = R.string.show_more)

                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = contentDescription,
                )
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

@Preview(showBackground = true, widthDp = 320, heightDp = 720)
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

@Preview(
    showBackground = true, widthDp = 320, heightDp = 320,
    uiMode = UI_MODE_NIGHT_YES, name = "Dark mode"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview() {
    ComposeTutorialTheme {
        Greetings(modifier = Modifier.fillMaxSize())
    }
}

