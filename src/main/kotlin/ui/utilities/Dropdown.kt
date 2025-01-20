package ui.utilities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropDown(levels:List<String>, onSelect:(String) -> Unit) {

    var isClick by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf(levels[0]) }

    Card(Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        elevation = 4.dp) {
        Column(
            Modifier.fillMaxWidth()
                .wrapContentHeight()) {
            Row(Modifier
                .fillMaxWidth()
                .heightIn(50.dp, 60.dp)
                .padding(8.dp)
                .clickable {
                    isClick = !isClick },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1F, true),
                    contentAlignment = Alignment.CenterStart) {
                    Text(selectedLevel, style = MaterialTheme.typography.body2)
                }
                Box(Modifier
                    .fillMaxHeight()
                    .padding(3.dp),
                    contentAlignment = Alignment.Center) {

                    Icon(imageVector = Icons.Default.ArrowDropDown, "dropdown")
                }
            }
            Spacer(Modifier.height(2.dp))
            if(isClick) {
                    levels.forEach {
                        Card {
                            Row(Modifier.fillMaxWidth()
                                .height(40.dp)
                                .padding(8.dp)
                                .clickable {
                                    selectedLevel = it
                                    onSelect(it)
                                    isClick = !isClick
                                }
                                ,
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(it, style = MaterialTheme.typography.body2)
                            }
                        }
                        Spacer(Modifier.height(1.dp))
                    }

            }
        }
    }
}