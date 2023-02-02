import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme(darkColorScheme()) {
            Surface(modifier = Modifier.fillMaxSize()) {
                var f by remember { mutableStateOf(1) }
                var f1 by remember { mutableStateOf(1) }

                LaunchedEffect(f) {
                    println("NoUpdatedState: $f")
                }

                Column {
                    ListItem(
                        headlineText = { Text("Plus 1") },
                        modifier = Modifier.clickable {
                            f++
                            f1++
                        }
                    )

                    LandingScreen(
                        f, f1
                    ) { println("Hello!") }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(timeoutSpec: Int, ts: Int, onTimeout: () -> Unit) {

    // This will always refer to the latest onTimeout function that
    // LandingScreen was recomposed with
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    val currentTimeoutSpec by rememberUpdatedState(timeoutSpec)
    val currentTimeoutSpec2 by remember(timeoutSpec) { mutableStateOf(timeoutSpec) }
    val currentTimeoutSpec3 by remember { mutableStateOf(timeoutSpec) }.apply { value = timeoutSpec }

    // Create an effect that matches the lifecycle of LandingScreen.
    // If LandingScreen recomposes, the delay shouldn't start again.
    LaunchedEffect(true) {
        //switch database, get data, profit
        delay(100L)
        currentOnTimeout()
        //get network
    }

    var timeoutSpec1 by remember { mutableStateOf(timeoutSpec) }
    var currentTimeoutSpecT1 by remember { mutableStateOf(currentTimeoutSpec) }
    var currentTimeoutSpec2T1 by remember { mutableStateOf(currentTimeoutSpec2) }
    var currentTimeoutSpec3T1 by remember { mutableStateOf(currentTimeoutSpec3) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100L)
            println(timeoutSpec) // this will print the first snapshot that it gets
            timeoutSpec1 = timeoutSpec
            println(currentTimeoutSpec) // this will ALWAYS print the latest value
            currentTimeoutSpecT1 = currentTimeoutSpec
            println(currentTimeoutSpec2) // this will print the first snapshot that it gets
            currentTimeoutSpec2T1 = currentTimeoutSpec2
            println(currentTimeoutSpec3)// this will ALWAYS print the latest value
            currentTimeoutSpec3T1 = currentTimeoutSpec3
            println("--------------------------------------")
        }
    }

    /* Landing screen content */
    Text(currentTimeoutSpec.toString())
    Text(timeoutSpec.toString())
    Text(ts.toString())
    Divider()
    ListItem(
        headlineText = { Text("TimeoutSpec (just the value from the parameter)") },
        leadingContent = { Text(timeoutSpec1.toString()) }
    )
    ListItem(
        headlineText = { Text("CurrentTimeoutSpec (rememberUpdatedState)") },
        leadingContent = { Text(currentTimeoutSpecT1.toString()) }
    )
    ListItem(
        headlineText = { Text("CurrentTimeoutSpec2 (remember(timeoutSpec) { mutableStateOf(timeoutSpec) })") },
        leadingContent = { Text(currentTimeoutSpec2T1.toString()) }
    )
    ListItem(
        headlineText = { Text("CurrentTimeoutSpec3 (the code from rememberUpdatedState)") },
        leadingContent = { Text(currentTimeoutSpec3T1.toString()) }
    )
}