package com.arctouch.heronsanches.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arctouch.heronsanches.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTutorialTheme {
                Surface {
                    /*MessageCard(
                        Message(
                            "Android", "Hey, take a look at Jetpack Compose, it's great!"
                        )
                    )*/
                    Conversation(messages = SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val message: String)

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            Log.d("hhh", "LazyColumn message $message")
            MessageCard(message = message)
        }
    }
}

@Composable
fun MessageCard(message: Message) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        Log.d("hhh", "MessageCard->Row message: $message")
        Image(
            painter = painterResource(id = R.drawable.img_heron_profile),
            contentDescription = "Profile image",

            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape),
        )
        Spacer(modifier = Modifier.width(8.dp))
        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Log.d("hhh", "Column(modifier = Modifier.clickable { /*isExpanded = !isExpanded*/ })")
            Text(
                text = message.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp,
                color = surfaceColor, modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Log.d("hhh", "Column->Surface")
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}


@Preview(
    name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@Preview(name = "Light Mode")
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(Message("Colleague", "Message"))
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}