package com.charros_software.proceso_enfermeria.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.charros_software.proceso_enfermeria.R
import com.charros_software.proceso_enfermeria.data.nandaList
import com.charros_software.proceso_enfermeria.data.nicList
import com.charros_software.proceso_enfermeria.data.nocList
import com.charros_software.proceso_enfermeria.data.room.AppDatabase
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.data.toDomain
import com.charros_software.proceso_enfermeria.ui.viewmodel.CollectionVUiState
import com.charros_software.proceso_enfermeria.ui.viewmodel.CollectionVViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CollectionViewScreen(
    navController: NavController, collectionId: String?
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = RoomRepository(
        database.nursingProcessDiagnosticsDao(),
        database.nursingProcessInterventionsDao(),
        database.nursingProcessResultsDao(),
        database.nursingProcessCollectionDao(),
        database.favoriteDiagnosticDao(),
        database.favoriteInterventionDao(),
        database.favoriteResultDao()
    )
    val idCollection = collectionId?.toInt() ?: 0
    val collectionVViewModel = CollectionVViewModel(repository, idCollection,
        nandaList.map { it.toDomain() },
        nicList.map { it.toDomain() },
        nocList.map { it.toDomain() })
    val collectionVUiState by collectionVViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBarCollectionViewScreen(collectionVUiState) }
    ) {
        ContentCollectionViewScreen(navController, collectionVViewModel, collectionVUiState)
    }
}

@Composable
fun ContentCollectionViewScreen(
    navController: NavController,
    collectionVViewModel: CollectionVViewModel,
    collectionVUiState: CollectionVUiState
) {
    var expandCardDiagnostics by remember { mutableStateOf(false) }
    var expandCardInterventions by remember { mutableStateOf(false) }
    var expandCardResults by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = stringResource(id = R.string.title_diagnostics),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    FilledIconButton(
                        onClick = { expandCardDiagnostics = !expandCardDiagnostics }
                    ) {
                        Icon(imageVector = if (expandCardDiagnostics) Icons.Filled.ArrowCircleUp else Icons.Filled.ArrowCircleDown,
                            contentDescription = stringResource(id = R.string.cd_expand_icon))
                    }
                }
                AnimatedVisibility(
                    visible = expandCardDiagnostics,
                    enter = fadeIn(initialAlpha = 0.4f),
                    exit = fadeOut(animationSpec = tween(durationMillis = 250))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (collectionVUiState.currentDiagnosticsList.isNotEmpty()) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(.99f).padding(2.dp),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            collectionVUiState.currentDiagnosticsList.forEach { diagnostic ->
                                ListItem(
                                    headlineContent = { Text(diagnostic!!.diagnostic) },
                                    overlineContent = {
                                        Text(String.format("# %04d", diagnostic!!.number))
                                    },
                                    trailingContent = {
                                        OutlinedIconButton(onClick = { collectionVViewModel.deleteDiagnosticCollection(diagnostic!!.number) }) {
                                            Icon(imageVector = Icons.Outlined.DeleteOutline, contentDescription = stringResource(id = R.string.cd_delete_icon))
                                        }
                                    }
                                )
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth(.99f)
                                        .padding(vertical = 1.dp),
                                    thickness = 1.dp, MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = stringResource(id = R.string.title_interventions),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    FilledIconButton(
                        onClick = { expandCardInterventions = !expandCardInterventions }
                    ) {
                        Icon(imageVector = if (expandCardInterventions) Icons.Filled.ArrowCircleUp else Icons.Filled.ArrowCircleDown,
                            contentDescription = stringResource(id = R.string.cd_expand_icon))
                    }
                }
                AnimatedVisibility(
                    visible = expandCardInterventions,
                    enter = fadeIn(initialAlpha = 0.4f),
                    exit = fadeOut(animationSpec = tween(durationMillis = 250))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (collectionVUiState.currentInterventionsList.isNotEmpty()) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(.99f).padding(2.dp),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            collectionVUiState.currentInterventionsList.forEach { intervention ->
                                ListItem(
                                    headlineContent = { Text(intervention!!.intervention) },
                                    overlineContent = {
                                        Text(String.format("# %04d", intervention!!.number))
                                    },
                                    trailingContent = {
                                        OutlinedIconButton(onClick = { collectionVViewModel.deleteInterventionCollection(intervention!!.number) }) {
                                            Icon(imageVector = Icons.Outlined.DeleteOutline, contentDescription = stringResource(id = R.string.cd_delete_icon))
                                        }
                                    }
                                )
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth(.99f)
                                        .padding(vertical = 1.dp),
                                    thickness = 1.dp, MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = stringResource(id = R.string.title_results),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    FilledIconButton(
                        onClick = { expandCardResults = !expandCardResults }
                    ) {
                        Icon(imageVector = if (expandCardResults) Icons.Filled.ArrowCircleUp else Icons.Filled.ArrowCircleDown,
                            contentDescription = stringResource(id = R.string.cd_expand_icon))
                    }
                }
                AnimatedVisibility(
                    visible = expandCardResults,
                    enter = fadeIn(initialAlpha = 0.4f),
                    exit = fadeOut(animationSpec = tween(durationMillis = 250))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (collectionVUiState.currentResultsList.isNotEmpty()) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(.99f).padding(2.dp),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            collectionVUiState.currentResultsList.forEach { result ->
                                ListItem(
                                    headlineContent = { Text(result!!.result) },
                                    overlineContent = {
                                        Text(String.format("# %04d", result!!.number))
                                    },
                                    trailingContent = {
                                        OutlinedIconButton(onClick = { collectionVViewModel.deleteResultCollection(result!!.number) }) {
                                            Icon(imageVector = Icons.Outlined.DeleteOutline, contentDescription = stringResource(id = R.string.cd_delete_icon))
                                        }
                                    }
                                )
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth(.99f)
                                        .padding(vertical = 1.dp),
                                    thickness = 1.dp, MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCollectionViewScreen(collectionVUiState: CollectionVUiState) {
    TopAppBar(title = { Text("${stringResource(id = R.string.title_collections)} - ${collectionVUiState.currentCollection?.name ?: ""}") })
}