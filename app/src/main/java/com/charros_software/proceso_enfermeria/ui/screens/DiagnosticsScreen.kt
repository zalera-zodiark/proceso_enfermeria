package com.charros_software.proceso_enfermeria.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddToPhotos
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.charros_software.proceso_enfermeria.R
import com.charros_software.proceso_enfermeria.data.nandaList
import com.charros_software.proceso_enfermeria.data.room.AppDatabase
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.data.toDomain
import com.charros_software.proceso_enfermeria.ui.viewmodel.DiagnosticUiState
import com.charros_software.proceso_enfermeria.ui.viewmodel.DiagnosticViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiagnosticsScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = RoomRepository(
        database.nursingProcessDiagnosticsDao(),
        database.nursingProcessCollectionDao(),
        database.favoriteDiagnosticDao())
    val diagnosticViewModel = DiagnosticViewModel(repository)
    val diagnosticUiState by diagnosticViewModel.uiState.collectAsState()
    diagnosticViewModel.initDiagnosticsList(nandaList.map { it.toDomain() })

    Scaffold(
        topBar = { TopAppBarDiagnosticScreen(diagnosticViewModel) }
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
            if (diagnosticUiState.favoriteListLoaded) {
                items(diagnosticUiState.currentDiagnosticsList.size) {
                    val diagnosticFavorite = diagnosticUiState.favoriteDiagnosticsList.find { favoriteDiagnostic ->
                        favoriteDiagnostic.diagnosticId == diagnosticUiState.currentDiagnosticsList[it].number
                    }
                    val isFavorite = diagnosticFavorite?.isFavorite ?: false
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.ListItem(
                            headlineContent = {
                                Text(text = diagnosticUiState.currentDiagnosticsList[it].diagnostic)
                            },
                            overlineContent = {
                                Text(
                                    text = String.format(
                                        "# %04d",
                                        diagnosticUiState.currentDiagnosticsList[it].number
                                    )
                                )
                            },
                            supportingContent = {
                                Column {
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth(.9f)
                                            .padding(vertical = 2.dp),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(text = "${stringResource(id = R.string.label_class)}: ${diagnosticUiState.currentDiagnosticsList[it].klass}")
                                    Text(text = "${stringResource(id = R.string.label_domain)}: ${diagnosticUiState.currentDiagnosticsList[it].domain}")
                                }
                            },
                            trailingContent = {
                                Column {
                                    IconButton(onClick = {
                                        diagnosticViewModel.setFavoriteDiagnostic(diagnosticUiState.currentDiagnosticsList[it].number, !isFavorite)
                                    }) {
                                        Icon(
                                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                            contentDescription = stringResource(id = R.string.cd_favourite_icon)
                                        )
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Outlined.AddToPhotos,
                                            contentDescription = stringResource(id = R.string.cd_add_collection_icon)
                                        )
                                    }
                                }
                            }
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        thickness = 4.dp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDiagnosticScreen(diagnosticViewModel: DiagnosticViewModel) {
    var favoriteListSelected by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = { Text(stringResource(id = R.string.title_diagnostics)) },
        actions = {
            IconButton(onClick = {
                diagnosticViewModel.filterFavoriteDiagnosticList(!favoriteListSelected, null)
                favoriteListSelected = !favoriteListSelected
            }) {
                Icon(
                    imageVector = if (favoriteListSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDiagnosticScreen() {

}