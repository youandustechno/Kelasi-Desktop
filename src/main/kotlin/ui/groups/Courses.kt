package ui.groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.LocalizedStrings
import ui.LocalizedStrings.ORGANIZATION_NAME
import ui.NavHelper
import ui.Route
import ui.utilities.AddCoursesCards


@Composable
fun Group(header: @Composable () () -> Unit, onClick: (NavHelper) -> Unit) {
    val numbers = (0..60).toList()

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        item {
            header()
        }

        item {
            LazyVerticalGrid(
                modifier = Modifier
                    .width(1000.dp)
                    .height(800.dp)
                    .padding(start = 50.dp, end = 20.dp)
                ,
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                items(numbers.size) {
                    Column(
                        Modifier.wrapContentHeight()
                            .wrapContentWidth()
                            .padding(10.dp)
                            .clickable {
                                onClick.invoke(NavHelper(Route.UserSubscription))
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        AddCoursesCards {
                            //VideoImageUrl()
                            Text(text = "${LocalizedStrings.get(ORGANIZATION_NAME)}: $it")
                        }
                    }
                }
            }
        }
    }
}

