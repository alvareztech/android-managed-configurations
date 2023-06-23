package tech.alvarez.managedconfigurationssample

import android.content.RestrictionsManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.alvarez.managedconfigurationssample.ui.theme.ManagedConfigurationsSampleTheme


class MainActivity : ComponentActivity() {
    private var serverUrlText by mutableStateOf("")
    private var authenticationEnabledText by mutableStateOf("")
    private var numberOfAttemptsText by mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManagedConfigurationsSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(title = {
                                Text("Managed Configurations")
                            })
                        },
                        floatingActionButton = {
                            ExtendedFloatingActionButton(
                                onClick = { getConfigurations() },
                                icon = { Icon(Icons.Filled.Refresh, contentDescription = "") },
                                text = { Text("Refresh") }
                            )
                        }) { contentPadding ->

                        Column(modifier = Modifier.padding(contentPadding)) {

                            ListItem(
                                headlineText = { Text(serverUrlText) },
                                overlineText = { Text("String") },
                                leadingContent = { Text(stringResource(R.string.server_url)) },
                            )
                            ListItem(
                                headlineText = { Text(authenticationEnabledText) },
                                overlineText = { Text("Bool") },
                                leadingContent = { Text(stringResource(R.string.authentication_enabled)) },
                            )
                            ListItem(
                                headlineText = { Text(numberOfAttemptsText) },
                                overlineText = { Text("Integer") },
                                leadingContent = { Text(stringResource(R.string.number_of_attempts)) },
                            )
                        }
                    }
                }
            }
        }

        getConfigurations()
    }

    private fun getConfigurations() {
        val restrictionsManager = this.getSystemService(RESTRICTIONS_SERVICE) as RestrictionsManager
        val appRestrictions = restrictionsManager.applicationRestrictions

        val serverUrl = appRestrictions.getString("server_url")
        serverUrlText = serverUrl ?: "<NULL>"

        val authenticationEnabled = appRestrictions.getBoolean("authentication_enabled")
        authenticationEnabledText = authenticationEnabled.toString()

        val numberOfAttempts = appRestrictions.getInt("number_attempts")
        numberOfAttemptsText = numberOfAttempts.toString()
    }
}
