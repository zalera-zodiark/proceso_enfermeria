package com.charros_software.proceso_enfermeria.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddToPhotos
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.charros_software.proceso_enfermeria.R
import com.charros_software.proceso_enfermeria.data.nandaList
import com.charros_software.proceso_enfermeria.data.toDomain
import com.charros_software.proceso_enfermeria.ui.viewmodel.DiagnosticUiState
import com.charros_software.proceso_enfermeria.ui.viewmodel.DiagnosticViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiagnosticsScreen(
    navController: NavController,
    diagnosticViewModel: DiagnosticViewModel = DiagnosticViewModel()
) {

    val diagnosticUiState by diagnosticViewModel.uiState.collectAsState()
    diagnosticViewModel.initDiagnosticsList(nandaList.map { it.toDomain() })

    Scaffold(
        topBar = { TopAppBarDiagnosticScreen() }
    ) {
        ContentDiagnosticScreen(navController, diagnosticViewModel, diagnosticUiState)
    }
}

@Composable
fun ContentDiagnosticScreen(
    navController: NavController,
    diagnosticViewModel: DiagnosticViewModel,
    diagnosticUiState: DiagnosticUiState) {

    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier
                .border(
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth(.9f),
            value = query,
            onValueChange = { query = it },
            trailingIcon = { Icon(Icons.Filled.Search, contentDescription = stringResource(id = R.string.cd_search_icon)) },
            placeholder = {Text(stringResource(id = R.string.place_holder_search))}
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(diagnosticUiState.currentDiagnosticsList.size) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.ListItem(
                        headlineContent = {
                            Text(text = diagnosticUiState.currentDiagnosticsList[it].diagnostic) },
                        overlineContent = { Text(text = String.format("# %04d", diagnosticUiState.currentDiagnosticsList[it].number))},
                        supportingContent = {
                            Column {
                                HorizontalDivider(modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .padding(vertical = 2.dp), color = MaterialTheme.colorScheme.primary)
                                Text(text = "${stringResource(id = R.string.label_class)}: ${diagnosticUiState.currentDiagnosticsList[it].klass}")
                                Text(text = "${stringResource(id = R.string.label_domain)}: ${diagnosticUiState.currentDiagnosticsList[it].domain}")
                            }
                        },
                        trailingContent = {
                            Column {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = stringResource(id = R.string.cd_favourite_icon))
                                }
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.AddToPhotos,
                                        contentDescription = stringResource(id = R.string.cd_add_collection_icon))
                                }
                            }
                        }
                    )
                }
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                    thickness = 4.dp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDiagnosticScreen() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.title_diagnostics)) }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDiagnosticScreen() {

}