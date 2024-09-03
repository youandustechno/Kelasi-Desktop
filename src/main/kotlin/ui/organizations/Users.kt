package ui.organizations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.NavHelper
import ui.utilities.UsersCards
import ui.utilities.VideoImageUrl


@Composable
fun UsersSubscription( onClick:(NavHelper) -> Unit) {

    val numbers = (0..60).toList()

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        item {
            LazyVerticalGrid(
                modifier = Modifier
                    .width(1000.dp)
                    .height(800.dp)
                    .padding(start = 50.dp, end = 20.dp)
                ,
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                items(numbers.size) {
                    Column(
                        Modifier.wrapContentWidth()
                            .wrapContentHeight()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        UsersCards {
                            VideoImageUrl()
                            Text(text = "  $it")
                        }
                    }
                }
            }
        }
    }
}

