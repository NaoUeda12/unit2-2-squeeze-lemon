package com.example.squeezelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.squeezelemon.ui.theme.SqueezeLemonTheme
import kotlin.collections.mutableSetOf as mutableSetOf1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SqueezeLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp(

                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun LemonadeApp(modifier: Modifier) {
    var onImageTapped = remember { mutableStateOf(1) }
    var squeezeCount = remember { mutableStateOf((2..4).random()) }


    Scaffold(
        topBar = { SmallTopAppBar() },
        content = { paddingValues ->
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues) 
            ) {

                when (onImageTapped.value) {
                    1 -> {
                        LemonTextAndImage(
                            textLabelResourceId = R.string.lemon_tree,
                            drawableResourceId = R.drawable.lemon_tree,
                            contentDescriptionResourceId = null,
                            onImageClick = {
                                onImageTapped.value = 2 // タップで次の状態に遷移
                                squeezeCount.value = (2..4).random()
                            }
                        )

                    }

                    2 -> {
                        LemonTextAndImage(
                            textLabelResourceId = R.string.lemon_squueze,
                            drawableResourceId = R.drawable.lemon_squeeze,
                            contentDescriptionResourceId = null,
                            onImageClick = {
                                if (--squeezeCount.value == 0) {
                                    onImageTapped.value = 3
                                }
                            }
                        )
                    }

                    3 -> {
                        LemonTextAndImage(
                            textLabelResourceId = R.string.lemon_drink,
                            drawableResourceId = R.drawable.lemon_drink,
                            contentDescriptionResourceId = null,
                            onImageClick = {
                                onImageTapped.value = 4
                            }
                        )
                    }

                    4 -> {
                        LemonTextAndImage(
                            textLabelResourceId = R.string.lemon_glass,
                            drawableResourceId = R.drawable.lemon_restart,
                            contentDescriptionResourceId = null,
                            onImageClick = {
                                onImageTapped.value = 1
                            }
                        )
                    }
                }
            }
        })
}

@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int?,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onImageClick,
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow))
        ) {
            Image(
                painter = painterResource(drawableResourceId),
                contentDescription = null,
                modifier = Modifier
                    .width(128.dp)
                    .height(160.dp)

            )
        }
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Text(text = stringResource(textLabelResourceId))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.yellow),
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "Lemonade",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SqueezeLemonPreview() {
    SqueezeLemonTheme {
        LemonadeApp(modifier = Modifier)
    }
}