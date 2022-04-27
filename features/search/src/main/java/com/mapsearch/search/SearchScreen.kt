package com.mapsearch.search


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mapsearch.search.dto.HubItem
import com.mapsearch.search.viewmodel.SearchUiState
import com.mapsearch.search.viewmodel.SearchViewModel
import com.mapsearch.uikit.textfields.SearchTextField


@Composable
fun SearchResultScreen(navController: NavHostController) {
    val vm: SearchViewModel = hiltViewModel()
    Scaffold(
        backgroundColor = colorResource(id = R.color.white),
        topBar = { ScreenHeader(vm = vm) },
        content = { ScreenContent(vm = vm, navController) }
    )
}

@Composable
fun ScreenContent(vm: SearchViewModel, navController: NavHostController) {
    when (val state = vm.uiState.collectAsState().value) {
        is SearchUiState.ItemSelected -> {
            navController.navigateUp()
        }
        is SearchUiState.Success -> {
            ProductsList(vm, state.hubs)
        }
        is SearchUiState.Loading -> {
//                Shimmer(SHIMMER_SIZE)
        }
        else -> {}
    }
}

@Composable
fun ProductsList(vm: SearchViewModel, hubs: List<HubItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
    ) {
        items(items = hubs, key = { hub -> hub.id }) { hub ->
            ListItem(vm, hub)
        }
    }
}

@Composable
fun ListItem(vm: SearchViewModel, item: HubItem) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .clickable {
                vm.selectItem(item.id)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            text = item.name,
            fontSize = 16.sp,
            color = colorResource(com.mapsearch.uikit.R.color.black)
        )
    }
}

@Composable
fun ScreenHeader(vm: SearchViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MaterialTheme.colors.primary,
        elevation = 8.dp,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            SearchTextField(
                placeholder = R.string.search_placeholder,
                onValueChange = {
                    vm.startSearchWithDelay(it)
                })
        }
    }
}
