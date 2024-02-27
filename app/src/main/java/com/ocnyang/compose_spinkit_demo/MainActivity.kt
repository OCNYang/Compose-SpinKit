package com.ocnyang.compose_spinkit_demo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ocnyang.compose_loading.ArcInRing
import com.ocnyang.compose_loading.ChasingDots
import com.ocnyang.compose_loading.ChasingTwoDots
import com.ocnyang.compose_loading.Circle
import com.ocnyang.compose_loading.CircleWindmill
import com.ocnyang.compose_loading.CubeGrid
import com.ocnyang.compose_loading.DoubleArc
import com.ocnyang.compose_loading.DoubleBounce
import com.ocnyang.compose_loading.DoubleRhombus
import com.ocnyang.compose_loading.DoubleSector
import com.ocnyang.compose_loading.FadingCircle
import com.ocnyang.compose_loading.FoldingCube
import com.ocnyang.compose_loading.InstaSpinner
import com.ocnyang.compose_loading.OppositeArc
import com.ocnyang.compose_loading.Pulse
import com.ocnyang.compose_loading.Rainbow
import com.ocnyang.compose_loading.RotatingPlane
import com.ocnyang.compose_loading.ThreeArc
import com.ocnyang.compose_loading.ThreeBounce
import com.ocnyang.compose_loading.ThreeTadpole
import com.ocnyang.compose_loading.WanderingCubes
import com.ocnyang.compose_loading.Wave
import com.ocnyang.compose_loading.Windmill
import com.ocnyang.compose_spinkit_demo.ui.theme.ComposeSpinKitTheme

private const val TYPE_INDEX = "index"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getIntExtra(TYPE_INDEX, -1)

        setContent {
            ComposeSpinKitTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting(type = type)
                }
            }
        }
    }
}

private val colorList = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta, Color.Cyan)

private val Big = 2 to 80.dp
private val Normal = 4 to 40.dp
private val Boolean.Size: Pair<Int, Dp> get() = if (this) Big else Normal
private val Speeds = listOf("Normal" to 2000, "Fast" to 1000, "Slow" to 6000)

@Composable
fun Greeting(type: Int) {
    val showColor = remember { mutableStateOf(false) }
    val showName = remember { mutableStateOf(false) }
    val showBig = remember { mutableStateOf(false) }
    val showSpeedType = remember { mutableStateOf(0) }

    val solidColors = MaterialTheme.colorScheme.primary.let {
        listOf(Color(0xff333333), Color(0xff666666), Color(0xff999999), Color(0xffbbbbbb))
    }

    val colors = remember { mutableStateOf(solidColors) }

    LaunchedEffect(key1 = showColor.value, block = {
        if (showColor.value) {
            colors.value = colorList
        } else {
            colors.value = solidColors
        }
    })

    val list: List<Pair<@Composable () -> Unit, String>> = listOf(
        @Composable {
            DoubleSector(
                colors = colors.value,
                count = 2,
                size = showBig.value.Size.second,
                durationMillis = showSpeedType.value.let { Speeds[it].second }
            )
        } to "DoubleSector",
        @Composable {
            ArcInRing(
                modifier = Modifier.size(showBig.value.Size.second),
                ringColor = colors.value[0].copy(alpha = 0.3f),
                arcColor = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
            )
        } to "ArcInCircle",
        @Composable {
            Rainbow(
                colors = colors.value,
                size = showBig.value.Size.second,
                durationMillis = Speeds[showSpeedType.value].second
            )
        } to "Rainbow",
        @Composable {
            OppositeArc(
                colors = colors.value,
                modifier = Modifier.size(showBig.value.Size.second),
                durationMillis = Speeds[showSpeedType.value].second
            )
        } to "OppositeArc",
        @Composable {
            Windmill(
                colors = colors.value,
                modifier = Modifier.size(showBig.value.Size.second),
                durationMillis = Speeds[showSpeedType.value].second
            )
        } to "Windmill",
        @Composable {
            DoubleRhombus(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "DoubleRhombus",
        @Composable {
            ThreeTadpole(
                headColor = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                modifier = Modifier.size(showBig.value.Size.second)
            )
        } to "ThreeTadpole",
        @Composable {
            ThreeArc(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "ThreeArc",
        @Composable {
            DoubleArc(
                color = colors.value[0] to colors.value[1],
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "DoubleArc",
        @Composable {
            ChasingDots(
                colors = colors.value,
                size = showBig.value.Size.second,
                durationMillis = Speeds[showSpeedType.value].second
            )
        } to "ChasingDots",
        @Composable {
            ChasingTwoDots(
                color = colors.value[0] to colors.value[1],
                durationMillis = Speeds[showSpeedType.value].second,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "ChasingTwoDots",
        @Composable {
            Circle(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "Circle",
        @Composable {
            CubeGrid(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "CubeGrid",
        @Composable {
            DoubleBounce(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "DoubleBounce",
        @Composable {
            FadingCircle(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "FadingCircle",
        @Composable {
            FoldingCube(
                color = colors.value[0],
                durationMillisPerFraction = Speeds[showSpeedType.value].second / 4,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "FoldingCube",
        @Composable {
            InstaSpinner(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "InstaSpinner",
        @Composable {
            Pulse(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "Pulse",
        @Composable {
            RotatingPlane(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "RotatingPlane",
        @Composable {
            ThreeBounce(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "ThreeBounce",
        @Composable {
            WanderingCubes(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = DpSize(showBig.value.Size.second, showBig.value.Size.second)
            )
        } to "WanderingCubes",
        @Composable {
            Wave(
                color = colors.value[0],
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "Wave",
        @Composable {
            CircleWindmill(
                colors = colors.value,
                durationMillis = Speeds[showSpeedType.value].second,
                size = showBig.value.Size.second
            )
        } to "CircleWindmill",
    )

    Column {
        if (type == -1) {
            val context = LocalContext.current
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth().weight(1f),
                columns = GridCells.Fixed(showBig.value.Size.first),
                content = {
                    itemsIndexed(list) { index, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(0.5.dp)
                                .background(Color.White).clickable {
                                    context.startActivity(Intent(context, MainActivity::class.java).apply { putExtra(TYPE_INDEX, index) })
                                },
                            contentAlignment = Alignment.Center,
                            content = {
                                item.first.invoke()
                                if (showName.value) {
                                    Text(
                                        modifier = Modifier.align(Alignment.BottomCenter),
                                        text = item.second,
                                        fontSize = 10.sp,
                                        color = Color.LightGray,
                                    )
                                }
                            }
                        )
                    }
                })
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(0.5.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center,
                content = {
                    list[type].first.invoke()
                    if (showName.value) {
                        Text(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            text = list[type].second,
                            fontSize = 10.sp,
                            color = Color.LightGray,
                        )
                    }
                }
            )
        }

        NavigationBar(containerColor = Color.White, modifier = Modifier.shadow(elevation = 3.dp)) {
            Column {
                Row {
                    TextCheckBox(modifier = Modifier.wrapContentHeight().weight(1f), checked = showName, label = "ViewName")
                    TextCheckBox(modifier = Modifier.wrapContentHeight().weight(1f), checked = showColor, label = "Colorful")
                    TextCheckBox(modifier = Modifier.wrapContentHeight().weight(1f), checked = showBig, label = "Big")
                }
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        modifier = Modifier.wrapContentHeight().weight(1f),
                        onClick = {
                            showSpeedType.value = (showSpeedType.value + 1) % Speeds.size
                        },
                        content = {
                            Text(text = "Speed: ${Speeds[showSpeedType.value].first}", fontSize = 12.sp)
                        })

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
fun TextCheckBox(modifier: Modifier, checked: MutableState<Boolean>, label: String) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
) {
    Checkbox(
        checked = checked.value,
        onCheckedChange = {
            checked.value = !checked.value
        })

    Text(text = label, fontSize = 12.sp)
}



