package moe.tlaster.dialog.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.dialog.AlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        Scaffold { innerPadding ->
            Box(Modifier.padding(innerPadding).fillMaxSize(), Alignment.TopCenter) {
                var isShowDialog by remember { mutableStateOf(false) }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(24.dp))
                    Text("Dialog Sample")
                    Button(onClick = { isShowDialog = true }) {
                        Text("show")
                    }
                }
                if (isShowDialog) {
                    AlertDialog(
                        onDismissRequest = { isShowDialog = false },
                        confirmButton = {
                            Button(onClick = { isShowDialog = false }) {
                                Text("Close")
                            }
                        },
                        title = {
                            Text("title")
                        },
                        text = {
                            Text("this is text.")
                        }
                    )
                }
            }
        }
    }
}