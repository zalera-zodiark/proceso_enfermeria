package com.charros_software.proceso_enfermeria.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.charros_software.proceso_enfermeria.R
import com.charros_software.proceso_enfermeria.data.room.AppDatabase
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.navigation.AppScreens
import com.charros_software.proceso_enfermeria.ui.viewmodel.CollectionUiState
import com.charros_software.proceso_enfermeria.ui.viewmodel.CollectionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CollectionsScreen(navController: NavController) {

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
    val collectionViewModel = CollectionViewModel(repository)
    val collectionUiState by collectionViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBarCollectionScreen() }
    ) {
        ContentCollectionScreen(navController, collectionViewModel, collectionUiState)
    }
}

@Composable
fun ContentCollectionScreen(
    navController: NavController,
    collectionViewModel: CollectionViewModel,
    collectionUiState: CollectionUiState
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            items(collectionUiState.currentCollectionsList.size) {
                ListItem(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    headlineContent = {
                        Text(text = collectionUiState.currentCollectionsList[it].name)
                    },
                    overlineContent = {
                        Text(text = collectionUiState.currentCollectionsList[it].date)
                    },
                    trailingContent = {
                        Column {
                            OutlinedIconButton(onClick = {
                                collectionViewModel.deleteCollection(collectionUiState.currentCollectionsList[it].idNursingProcessCollection)
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = stringResource(id = R.string.cd_delete_icon)
                                )
                            }
                            FilledIconButton(onClick = {
                                navController.navigate(AppScreens.CollectionViewScreen.route +
                                        "/${collectionUiState.currentCollectionsList[it].idNursingProcessCollection}")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowCircleRight,
                                    contentDescription = stringResource(id = R.string.cd_play_icon)
                                )
                            }
                        }
                    }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(vertical = 1.dp),
                    thickness = 1.dp, MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
    
    if (collectionUiState.showDeleteCollectionMessage) {
        Toast.makeText(LocalContext.current, stringResource(id = R.string.label_delete_collection_message), Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCollectionScreen() {
    TopAppBar(

        title = { Text(stringResource(id = R.string.title_collections)) },
    )
}