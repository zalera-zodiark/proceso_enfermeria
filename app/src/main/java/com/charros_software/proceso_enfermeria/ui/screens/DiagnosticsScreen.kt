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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddToPhotos
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
    val diagnosticViewModel = DiagnosticViewModel(repository, nandaList.map { it.toDomain() })
    val diagnosticUiState by diagnosticViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBarDiagnosticScreen(diagnosticViewModel) }
    ) {
        ContentDiagnosticScreen(navController, diagnosticViewModel, diagnosticUiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentDiagnosticScreen(
    navController: NavController,
    diagnosticViewModel: DiagnosticViewModel,
    diagnosticUiState: DiagnosticUiState) {

    var query by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var newCollection by rememberSaveable { mutableStateOf("") }
    var diagnosticSelectedToAddCollection by remember { mutableIntStateOf(0) }

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
                                    IconButton(onClick = {
                                        diagnosticSelectedToAddCollection = diagnosticUiState.currentDiagnosticsList[it].number
                                        showBottomSheet = true
                                    }) {
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
        if (showBottomSheet) {

            ModalBottomSheet(
                modifier = Modifier.padding(bottom = 8.dp),
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(.85f)
                            .border(
                                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        value = newCollection,
                        onValueChange = {
                            if (diagnosticUiState.isCollectionDuplicateError) {
                                diagnosticViewModel.setOffCollectionDuplicateError()
                            }
                            newCollection = it},
                        label = { Text(stringResource(R.string.label_new_collection)) },
                        singleLine = true,
                        isError = diagnosticUiState.isCollectionDuplicateError,
                        supportingText = {
                            if (diagnosticUiState.isCollectionDuplicateError) {
                                Text(stringResource(id = R.string.label_collection_duplicate_error))
                            } else {
                                Text("")
                            }
                        }
                    )
                    FilledIconButton(
                        modifier = Modifier.padding(start = 5.dp),
                        onClick = {
                            diagnosticViewModel.addNewCollection(newCollection)
                            newCollection = ""
                        }
                    ) { Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.button_new_collection)) }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    item {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(.99f)
                                .padding(top = 4.dp, bottom = 2.dp),
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(diagnosticUiState.collectionsList.size) {
                        ListItem(
                            headlineContent = { Text(diagnosticUiState.collectionsList[it].name) },
                            overlineContent = { Text(diagnosticUiState.collectionsList[it].date) },
                            trailingContent = { OutlinedIconButton(onClick = {
                                diagnosticViewModel
                                    .addDiagnosticToCollection(
                                        diagnosticUiState.collectionsList[it].idNursingProcessCollection,
                                        diagnosticSelectedToAddCollection) }) {
                                Icon(Icons.Filled.LibraryAdd, stringResource(id = R.string.cd_add_collection_icon))
                            } },
                            supportingContent = {
                                if (diagnosticUiState.isDiagnosticDuplicateError &&
                                    diagnosticUiState.idCollectionDiagnosticDuplicateError == diagnosticUiState.collectionsList[it].idNursingProcessCollection) {
                                    Text(
                                        stringResource(id = R.string.label_diagnostic_duplicate_error),
                                        color = Color.Red)
                                } else {
                                    Text("",
                                        color = Color.Black)
                                }
                            })
                        HorizontalDivider(modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(vertical = 1.dp),
                            thickness = 1.dp, MaterialTheme.colorScheme.tertiary)
                    }
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
