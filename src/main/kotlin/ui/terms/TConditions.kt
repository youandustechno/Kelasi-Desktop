package ui.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.LocalizedStrings
import ui.LocalizedStrings.CLOSE
import ui.LocalizedStrings.VALIDATE
import ui.NavHelper
import ui.Route
import ui.utilities.ConfirmButton
import ui.utilities.DenyButton
import ui.utilities.TConditionsCards
import ui.utilities.TermsAndConditionsTexts


@Composable
fun TermsAndConditions( onClick:((NavHelper) -> Unit)? = null) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Background color similar to screenshot
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            TermsAndConditionsTexts()
            Spacer(modifier = Modifier.height(40.dp))

            TConditionsCards()
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            ConfirmButton(LocalizedStrings.get(VALIDATE)) {
                onClick?.invoke(NavHelper(Route.AuthLogin))
            }

            Spacer(modifier = Modifier.height(20.dp))
            ConfirmButton(LocalizedStrings.get(CLOSE)) {
                // logout the user
            }
        }
    }
}