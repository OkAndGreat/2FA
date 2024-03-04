package widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@Composable
fun AnimatedCircleProgress(
    modifier: Modifier,
    curProgress: Int,
    maxProgress: Int,
    circleColor: Color = Color.Blue,
    circleStrokeWidth: Dp = 3.dp
) {
    val progress = (curProgress.toFloat() / maxProgress.toFloat())
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Box(modifier = modifier) {
        CircularProgressIndicator(
            color = circleColor,
            progress = animatedProgress,
            modifier = Modifier.fillMaxSize(),
            strokeWidth = circleStrokeWidth
        )
        Text(text = curProgress.toString(), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
@Preview
fun AnimatedCircleProgressWidgetPreview() {
    val progress = flow<Int> {
        repeat(100) {
            emit(it + 1)
            delay(50)
        }
    }.collectAsState(initial = 0)
    AnimatedCircleProgress(modifier = Modifier.size(50.dp), curProgress = progress.value, maxProgress = 30)
}

