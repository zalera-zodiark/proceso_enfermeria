package com.charros_software.proceso_enfermeria.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.charros_software.proceso_enfermeria.R
import com.charros_software.proceso_enfermeria.navigation.AppScreens
import com.charros_software.proceso_enfermeria.ui.theme.ProcesoEnfermeriaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    Scaffold {
       MainScreenContent(navController)
    }
}

@Composable
fun MainScreenContent(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
                    text = stringResource(id = R.string.title_diagnostics),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.SemiBold
                    )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paint(
                                painterResource(id = R.drawable.dos_enfermeras_revisando_documentos),
                                contentScale = ContentScale.FillWidth,
                                alpha = 0.6f
                            ),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { navController.navigate(AppScreens.DiagnosticsScreen.route) }) {
                            Text(text = stringResource(id = R.string.button_explore))
                        }
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
                    text = stringResource(id = R.string.title_interventions),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paint(
                                painterResource(id = R.drawable.enfermera_en_quirofano),
                                contentScale = ContentScale.FillWidth,
                                alpha = 0.6f
                            ),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = stringResource(id = R.string.button_explore))
                        }
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
                    text = stringResource(id = R.string.title_results),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paint(
                                painterResource(id = R.drawable.enfermera_entrevista_paciente),
                                contentScale = ContentScale.FillWidth,
                                alpha = 0.6f
                            ),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = stringResource(id = R.string.button_explore))
                        }
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
                    text = stringResource(id = R.string.title_save_lists),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paint(
                                painterResource(id = R.drawable.enfermera_guardando_expedientes),
                                contentScale = ContentScale.FillWidth,
                                alpha = 0.6f
                            ),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = stringResource(id = R.string.button_explore))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContentPreview() {
    ProcesoEnfermeriaTheme {
        MainScreenContent(NavController(LocalContext.current))
    }
}