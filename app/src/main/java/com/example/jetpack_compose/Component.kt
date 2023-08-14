package com.example.jetpack_compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint.Style
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SettingsAccessibility
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material3.ContentAlpha
import com.example.jetpack_compose.Darktheme.ThemeViewModel
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import com.example.jetpack_compose.ui.theme.OnPrimaryContainerLight
import com.example.jetpack_compose.ui.theme.OnPrimaryDark
import com.example.jetpack_compose.ui.theme.OnPrimaryLight
import com.example.jetpack_compose.ui.theme.OnSecondaryContainerLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.w3c.dom.Text
import java.time.format.TextStyle

object Component {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TextInput(lebel:String,icon: ImageVector,keyboardType: KeyboardType, Done:()->Unit):String
    {
        val state = remember { mutableStateOf("") }
        OutlinedTextField(
            value = state.value,
            onValueChange = { state.value = it },
            label = { Text(lebel) },
//            placeholder = { Text(text = "Subhajit", fontStyle = FontStyle.Italic) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
            maxLines = 1,
            leadingIcon = {
                Icon(
                   imageVector =  icon,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(CornerSize(30.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Yellow,
                focusedBorderColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth(.8f),
            keyboardOptions = KeyboardOptions(
// This is the type of keyboard, for example, text or number pad. Available options:
                keyboardType = keyboardType,
// it signals the keyboard what type of action should be displayed. The Enter key on the keyboard is changed according to the action.
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    Done()
                }
            )
        )
        return state.value.toString()
    }
    @Composable
    fun CardView(@DrawableRes id: Int, text: String, context: Context) {
        val focusRequester = remember {
            FocusRequester()
        }
        val interactionSource = remember {
            MutableInteractionSource()
        }
        val isFocused=interactionSource.collectIsFocusedAsState().value
        val extraPadding by animateDpAsState(
            targetValue = if (!isFocused ) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            modifier = Modifier
//                .padding(extraPadding.coerceAtLeast(10.dp))

        ) {
            Surface(
                modifier = Modifier
//                    .focusRequester(focusRequester = focusRequester)
//                    .focusable(interactionSource = interactionSource)
//                    .clickable() { focusRequester.requestFocus() }
            )
            {
                Column {
                    Image(
                        modifier = Modifier
                            .width(width = 300.dp)
                            .clip(shape = RoundedCornerShape(size = 12.dp)),
                        painter = painterResource(id = id),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 16.dp),
                        text = text,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

        }
    }

    fun toast(msg: String, context: Context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ExpandableCard(
        title: String,
        titleFontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
        titleFontWeight: FontWeight = FontWeight.Bold,
        description: String,
        descriptionFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
        descriptionFontWeight: FontWeight = FontWeight.Normal,
        descriptionMaxLines: Int = 4,
        shape: Shape = ShapeDefaults.Medium,
        padding: Dp = 12.dp
    ) {
        var expandedState by remember { mutableStateOf(false) }
        val rotationState by animateFloatAsState(
            targetValue = if (expandedState) 180f else 0f
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .padding(all = 10.dp),
            shape = shape,
            onClick = {
                expandedState = !expandedState
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f),
                        text = title,
                        fontSize = titleFontSize,
                        fontWeight = titleFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .alpha(ContentAlpha.medium)
                            .rotate(rotationState),
                        onClick = {
                            expandedState = !expandedState
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }
                }
                if (expandedState) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(size = 12.dp)),
                        painter = painterResource(id = R.drawable.layer),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = description,
                        fontSize = descriptionFontSize,
                        fontWeight = descriptionFontWeight,
                        maxLines = descriptionMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
    // single item layout for LazyColumn
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ItemLayout(
        optionsList: OptionsList,
        context: Context = LocalContext.current.applicationContext
    ) {
        Card(
            shape = RoundedCornerShape(size = 12.dp),
            onClick={Toast
                .makeText(context, optionsList.option, Toast.LENGTH_SHORT)
                .show()}

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(size = 36.dp),
                    imageVector = optionsList.icon,
                    contentDescription = null,
                    tint = Color(0xFFFF9800)
                )
                Spacer(modifier = Modifier.width(width = 12.dp))
                Text(
                    text = optionsList.option,
                    fontSize = 16.sp
                )
            }
        }
    }
    // prepare the list for LazyColumn
    fun prepareOptionsList(): MutableList<OptionsList> {
        val optionsList = mutableListOf<OptionsList>()

        optionsList.add(OptionsList(icon = Icons.Outlined.Favorite, option = "Saved Items"))
        optionsList.add(OptionsList(icon = Icons.Outlined.FavoriteBorder, option = "UnSaved Items"))
        optionsList.add(OptionsList(icon = Icons.Outlined.Paid, option = "Payment History"))
        optionsList.add(OptionsList(icon = Icons.Outlined.Lightbulb, option = "New Ideas"))
        optionsList.add(OptionsList(icon = Icons.Outlined.WatchLater, option = "Items History"))
        optionsList.add(OptionsList(icon = Icons.Outlined.Article, option = "Shared Articles"))
        optionsList.add(OptionsList(icon = Icons.Outlined.Notifications, option = "Previous Notifications"))
        optionsList.add(OptionsList(icon = Icons.Outlined.VerifiedUser, option = "Verification Badge"))
        optionsList.add(OptionsList(icon = Icons.Outlined.Pending, option = "Pending Tasks"))
        optionsList.add(OptionsList(icon = Icons.Outlined.QuestionAnswer, option = "FAQs"))
        optionsList.add(OptionsList(icon = Icons.Outlined.Help, option = "Support"))
        return optionsList
    }

    data class OptionsList(val icon: ImageVector, val option: String)
//   change statusbar color
    @Composable
    fun darkmode( context: Context)
    {
        val themeViewModel: ThemeViewModel = hiltViewModel()
        val themeState by themeViewModel.themeState.collectAsState()
        val dark =themeState.isDarkMode
        val sysUIcon= rememberSystemUiController()
        DisposableEffect( dark )
        {
            sysUIcon.setStatusBarColor(if (dark) OnSecondaryContainerLight else OnPrimaryLight)
            val Flags=sysUIcon.systemBarsBehavior
            val nf=if(dark)(Flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)else(Flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                sysUIcon.systemBarsBehavior=nf
            onDispose {  }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DialogBox(openSetting:()->Unit,onDismiss: () -> Unit) {
        val contextForToast = LocalContext.current.applicationContext
        Dialog(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
//                elevation = 4.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(color = Color(0xFF35898f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp),
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "2-Step Verification",
                            alignment = Alignment.Center
                        )
//                        Icon(imageVector = Icons.Outlined.SettingsAccessibility, contentDescription = )
                    }

                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = "Open Setting",
                        textAlign = TextAlign.Center,
                        style = androidx.compose.ui.text.TextStyle(
//                            fontFamily = FontFamily(Font(R.font., FontWeight.Bold)),

                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        text = "Your Location is turn off",
                        textAlign = TextAlign.Center,
                        style = androidx.compose.ui.text.TextStyle(
//                            fontFamily = FontFamily(Font(R.font., FontWeight.Normal)),
                            fontSize = 14.sp
                        )
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 36.dp, start = 36.dp, end = 36.dp, bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35898f)),
                        onClick = {
                            onDismiss()
                            openSetting()
                            Toast.makeText(
                                contextForToast,
                                "Click: Setup Now",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                        Text(
                            text = "Turn On Now",
                            color = Color.White,
                            style = androidx.compose.ui.text.TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        )
                    }

                    TextButton(
                        onClick = {
                            onDismiss()
                            Toast.makeText(
                                contextForToast,
                                "Click: I'll Do It Later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                        Text(
                            text = "I'll Do It Later",
                            color = Color(0xFF35898f),
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
        }
    }

}