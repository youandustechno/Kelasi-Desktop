package ui.organizations


//import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ui.LocalizedStrings
import ui.LocalizedStrings.CARD_NUMBER
import ui.LocalizedStrings.CVV
import ui.LocalizedStrings.EXPIRATION_DATE
import ui.LocalizedStrings.NAME_ON_CARD
import ui.LocalizedStrings.PAY_WITH_PAYPAL
import ui.LocalizedStrings.SUBSCRIPTION_PAYMENT
import ui.NavHelper
import ui.NavKeys.EMPTY

@Composable
fun CoursesSubscriptionList(onClick:((NavHelper) -> Unit)? = null, onPayClick: (() -> Unit)? = null) {
    var name by remember { mutableStateOf(EMPTY) }
    var cardNumber by remember { mutableStateOf(EMPTY) }
    var expirationDate by remember { mutableStateOf(EMPTY) }
    var cvv by remember { mutableStateOf(EMPTY) }

    Column(
        modifier = Modifier.padding(16.dp)
            .widthIn(400.dp, 700.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(LocalizedStrings.get(SUBSCRIPTION_PAYMENT), style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(LocalizedStrings.get(NAME_ON_CARD)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card Number Input
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text(LocalizedStrings.get(CARD_NUMBER)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            // Expiration Date Input
            OutlinedTextField(
                value = expirationDate,
                onValueChange = { expirationDate = it },
                label = { Text(LocalizedStrings.get(EXPIRATION_DATE)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )

            // CVV Input
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text(LocalizedStrings.get(CVV)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pay Button
        Button(
            onClick = { onPayClick?.invoke() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(LocalizedStrings.get(PAY_WITH_PAYPAL))
        }
    }
}
