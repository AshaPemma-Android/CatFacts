package com.example.catfacts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CatFactScreen(viewModel: ICatFactViewModel) {
    when (val state = viewModel.uiState.collectAsState().value) {
        is CatFactUiState.Idle -> {
            CatFactContent(
                factText = "Click the button to get a cat fact!",
                onFetchClick = { viewModel.getCatFact() }
            )
        }

        is CatFactUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("loading"),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CatFactUiState.Success -> {
            CatFactContent(
                factText = state.fact,
                onFetchClick = { viewModel.getCatFact() }
            )
        }

        is CatFactUiState.Error -> {
            CatFactContent(
                factText = state.message,
                onFetchClick = { viewModel.getCatFact() }
            )
        }
    }
}

@Composable
fun CatFactContent(
    factText: String,
    onFetchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = factText,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .testTag("catFactText")
        )

        Button(
            onClick = onFetchClick,
            modifier = Modifier.testTag("fetchButton")
        ) {
            Text(text = "Get New Fact")
        }
    }
}
