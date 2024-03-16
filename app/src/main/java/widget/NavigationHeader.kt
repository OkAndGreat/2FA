package widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twofa.utils.clickableWithoutRipple

@Composable
fun NavigationHeader(modifier: Modifier = Modifier, info: String, onBackClicked: (() -> Unit)) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "",
            modifier = Modifier
                .padding(end = 12.dp)
                .clickable {
                    onBackClicked.invoke()
                }
        )
        Text(text = info, fontSize = 28.sp)
    }
}

@Composable
@Preview
fun NavigationHeaderPreview() {
    NavigationHeader(info = "安全设置", onBackClicked = {})
}