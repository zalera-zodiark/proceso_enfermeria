package com.charros_software.proceso_enfermeria.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
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
import com.charros_software.proceso_enfermeria.data.nocList
import com.charros_software.proceso_enfermeria.data.room.AppDatabase
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.data.toDomain
import com.charros_software.proceso_enfermeria.ui.viewmodel.ResultUiState
import com.charros_software.proceso_enfermeria.ui.viewmodel.ResultViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResultsScreen(navController: NavController) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = RoomRepository(
        database.nursingProcessDiagnosticsDao(),
        database.nursingProcessInterventionsDao(),
        database.nursingProcessResultsDao(),
        database.nursingProcessCollectionDao(),
        database.favoriteDiagnosticDao(),
        database.favoriteInterventionDao(),
        database.favoriteResultDao())
    val resultViewModel = ResultViewModel(repository, nocList.map { it.toDomain() })
    val resultUiState by resultViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBarResultScreen(resultViewModel) }
    ) {
        ContentResultScreen(navController, resultViewModel, resultUiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentResultScreen(
    navController: NavController,
    resultViewModel: ResultViewModel,
    resultUiState: ResultUiState
) {
    var query by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var newCollection by rememberSaveable { mutableStateOf("") }
    var resultSelectedToAddCollection by remember { mutableIntStateOf(0) }

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
            onValueChange = {
                resultViewModel.searchValueChange(it)
                query = it },
            trailingIcon = { Icon(Icons.Filled.Search, contentDescription = stringResource(id = R.string.cd_search_icon)) },
            placeholder = { Text(stringResource(id = R.string.place_holder_search)) }
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
            items(resultUiState.currentResultsList.size) {
                val diagnosticFavorite = resultUiState.favoriteResultsList.find { favoriteDiagnostic ->
                    favoriteDiagnostic.resultId == resultUiState.currentResultsList[it].number
                }
                val isFavorite = diagnosticFavorite?.isFavorite ?: false
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListItem(
                        headlineContent = {
                            Text(text = resultUiState.currentResultsList[it].result)
                        },
                        overlineContent = {
                            Text(
                                text = String.format(
                                    "# %04d",
                                    resultUiState.currentResultsList[it].number
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
                                Text(text = "${stringResource(id = R.string.label_class)}: ${resultUiState.currentResultsList[it].klass}")
                                Text(text = "${stringResource(id = R.string.label_domain)}: ${resultUiState.currentResultsList[it].domain}")
                            }
                        },
                        trailingContent = {
                            Column {
                                IconButton(onClick = {
                                    resultViewModel.setFavoriteResult(resultUiState.currentResultsList[it].number, !isFavorite)
                                }) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = stringResource(id = R.string.cd_favourite_icon)
                                    )
                                }
                                IconButton(onClick = {
                                    resultSelectedToAddCollection = resultUiState.currentResultsList[it].number
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
                            if (resultUiState.isCollectionDuplicateError) {
                                resultViewModel.setOffCollectionDuplicateError()
                            }
                            newCollection = it},
                        label = { Text(stringResource(R.string.label_new_collection)) },
                        singleLine = true,
                        isError = resultUiState.isCollectionDuplicateError,
                        supportingText = {
                            if (resultUiState.isCollectionDuplicateError) {
                                Text(stringResource(id = R.string.label_collection_duplicate_error))
                            } else {
                                Text("")
                            }
                        }
                    )
                    FilledIconButton(
                        modifier = Modifier.padding(start = 5.dp),
                        onClick = {
                            resultViewModel.addNewCollection(newCollection)
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
                    items(resultUiState.collectionsList.size) {
                        ListItem(
                            headlineContent = { Text(resultUiState.collectionsList[it].name) },
                            overlineContent = { Text(resultUiState.collectionsList[it].date) },
                            trailingContent = { OutlinedIconButton(onClick = {
                                resultViewModel
                                    .addResultToCollection(
                                        resultUiState.collectionsList[it].idNursingProcessCollection,
                                        resultSelectedToAddCollection) }) {
                                Icon(Icons.Filled.LibraryAdd, stringResource(id = R.string.cd_add_collection_icon))
                            } },
                            supportingContent = {
                                if (resultUiState.isResultDuplicateError &&
                                    resultUiState.idCollectionResultDuplicateError == resultUiState.collectionsList[it].idNursingProcessCollection) {
                                    Text(
                                        stringResource(id = R.string.label_result_duplicate_error),
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
    if (resultUiState.showResultAddedToCollectionMessage) {
        showBottomSheet = false
        Toast.makeText(LocalContext.current,
            stringResource(id = R.string.label_add_result_to_collection), Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarResultScreen(resultViewModel: ResultViewModel) {
    var favoriteListSelected by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = { Text(stringResource(id = R.string.title_results)) },
        actions = {
            IconButton(onClick = {
                resultViewModel.filterFavoriteResultList(!favoriteListSelected, null)
                favoriteListSelected = !favoriteListSelected
            }) {
                Icon(
                    imageVector = if (favoriteListSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "")
            }
        }
    )
}