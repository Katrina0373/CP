package com.mocalovak.cp.presentation.Character

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.ui.theme.CPTheme
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.gradientButton
import com.mocalovak.cp.ui.theme.halfAppWhite
import com.mocalovak.cp.ui.theme.numBack
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.subTextColor
import com.mocalovak.cp.ui.theme.topContainer
import com.mocalovak.cp.utils.loadImageFromAssets
import kotlinx.coroutines.launch
import kotlin.math.floor

val cornerRadius = 14.dp
enum class ChangingValues{ Health, Mana, Gold}

@Composable
fun CharacterScreen(charVM: CharacterViewModel = hiltViewModel(),
                    characterId: Int,
                    onBackClick: () -> Unit,
                    navController: NavController){
    val character by charVM.character.collectAsState()

    when {
        character == null -> CircularProgressIndicator()
        else -> CharacterView(onBackClick, charVM, character!!, navController)
    }
}

@Composable
fun CharacterView(
    onBackClick: () -> Unit,
    charVM: CharacterViewModel = hiltViewModel(),
    character: Character,
    navController: NavController
    ){

    var isCommonInfoBoxExpanded by remember { mutableStateOf(true) }
    var showHealthDialog by remember { mutableStateOf(false) }
    var showManaDialog by remember { mutableStateOf(false) }
    var showGoldDialog by remember { mutableStateOf(false) }
    var showLanguagesDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = {TopBarCharacter(character, onBackClick)}, modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .padding(top = padding.calculateTopPadding(), bottom = 5.dp),
            ) {

            ExpandableBox(character = character, isExpanded = isCommonInfoBoxExpanded,
                onHealthClick = {showHealthDialog = true},
                onManaClick = {showManaDialog = true},
                onGoldClick = {showGoldDialog = true},
                increaseMana = {charVM.updateMana(character.currentMana + 1)},
                decreaseMana = { charVM.updateMana(character.currentMana - 1) },
                levelUp = { charVM.levelUp() }
            )

            CharacterStatsCard(character,
                closeExpandedBox = {isCommonInfoBoxExpanded = false},
                openEquipmentLibrary = {
                    navController.navigate("EquipmentLibraryWithAdding/${character.id}"){
                        launchSingleTop = true
                    }
                },
                openSkillLibrary = {
                    navController.navigate("SkillLibraryWithAdding/${character.id}"){
                        launchSingleTop = true
                    }
                }
            )

            Box(contentAlignment = Alignment.TopStart,
                modifier = Modifier.fillMaxWidth()
                    .padding(7.dp)
                    .background(color = containerColor,
                        shape = RoundedCornerShape(cornerRadius)
                    )
                    .clickable { showLanguagesDialog = true}
                    .padding(vertical = 13.dp, horizontal = 20.dp)) {
                Text(("Языки: " + character.languages?.joinToString(", ")),
                    maxLines = 1)
            }
            BottomActionButtons(onCheckClick = {

            },
                onAttackClick = {
                })

            if(showHealthDialog){
                ChangingDialog(
                    onDismiss = { showHealthDialog = false },
                    onConfirm = { newValue -> charVM.updateHP(newValue) },
                    label = "Здоровье",
                    changingValue = ChangingValues.Health,
                    currentValue = character.currentHP,
                    maxValue = character.maxHP,
                    temporaryValue = 0
                )
            }
            if(showManaDialog){
                ChangingDialog(
                    onDismiss = { showManaDialog = false },
                    onConfirm = { newValue -> charVM.updateMana(newValue) },
                    label = "Мана",
                    changingValue = ChangingValues.Mana,
                    currentValue = character.currentMana,
                    maxValue = character.maxMana,
                    temporaryValue = 0
                )
            }
            if(showGoldDialog){
                ChangingDialog(
                    onDismiss = { showGoldDialog = false },
                    onConfirm = { newValue -> charVM.updateGold(newValue)},
                    label = "Золото",
                    changingValue = ChangingValues.Gold,
                    currentValue = character.gold,
                    maxValue = null,
                    temporaryValue = 0
                )
            }

            if(showLanguagesDialog){
                LanguagesExplorer(
                    character.languages,
                    onConfirm = { value -> charVM.updateLanguages(value)
                                showLanguagesDialog = false},
                    onDismiss = {showLanguagesDialog = false}
                )
            }

        }
    }
}

@Composable
fun BottomActionButtons(
    onCheckClick: () -> Unit,
    onAttackClick: () -> Unit
) {

//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//            //.padding(bottom = 72.dp), // чтобы было над BottomBar
//        verticalArrangement = Arrangement.Bottom,
//        horizontalAlignment = Alignment.CenterHorizontally

    Box(
        modifier = Modifier.fillMaxHeight( ),
        contentAlignment = Alignment.BottomEnd
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GradientButton(
                text = "Проверка",
                icon = painterResource(id = R.drawable.d20_icon), // 🎲 твоя иконка
                gradient = Brush.horizontalGradient(
                    listOf(button2, button2)
                ),
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                onClick = onCheckClick
            )

            GradientButton(
                text = "Атака",
                icon = painterResource(id = R.drawable.swords_icon), // ⚔️ твоя иконка
                gradient = gradientButton,
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                onClick = onAttackClick
            )
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    icon: Painter? = null,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(gradient)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            //modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(text, color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            if(icon != null)
                Icon(
                    painter = icon,
                    contentDescription = text,
                    tint = Color.White
                )
        }
    }
}


@Composable
fun ExpandableBox(
    isExpanded: Boolean = true,
    character: Character,
    onHealthClick: () -> Unit,
    onManaClick: () -> Unit,
    onGoldClick: () -> Unit,
    increaseMana: () -> Unit,
    decreaseMana: () -> Unit,
    levelUp: () -> Unit
    ) {

    var expanded by remember { mutableStateOf(isExpanded) }

    val rotationState by animateFloatAsState(
        targetValue = if (!expanded) 180f else 0f,
        label = "arrowRotation"
    )

    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(color = containerColor,
                shape = RoundedCornerShape(cornerRadius))
            .padding(5.dp)
            .animateContentSize()
    ) {
        Box(contentAlignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)){
            Icon(
                painter = painterResource(R.drawable.row_up_icon),
                contentDescription = "row",
                modifier = Modifier.rotate(rotationState)
                    .clickable(onClick = {expanded = !expanded})
            )
        }

        if(expanded){
            Column(modifier = Modifier.padding(13.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly) {

                    Box(contentAlignment = Alignment.Center){
                        Image(painterResource(R.drawable.heart_icon),
                            contentDescription = "background",
                            modifier = Modifier.padding(top = 5.dp)
                                .clickable { onHealthClick() })

                        Text(text = "${character.currentHP}", fontSize = 20.sp)
                    }

                    Box(contentAlignment = Alignment.Center){
                        Image(painterResource(R.drawable.sheildicon), contentDescription = "background")
                        Text(text = "${floor(character.armorClass).toInt()}", fontSize = 20.sp)
                    }

                    Box(contentAlignment = Alignment.Center){
                        Image(painterResource(R.drawable.speedicon),
                            contentDescription = "background",
                            modifier = Modifier.padding(top = 3.dp)
                        )
                        Text(text = "${character.speed}", fontSize = 20.sp,
                            modifier = Modifier.padding(end = 12.dp))
                    }
                }
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(start = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(end = 5.dp)
                        ){
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Text("${character.gold}",
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onGoldClick() }
                                    .background(color = numBack)
                                    .padding(2.dp)
                                    .sizeIn(minWidth = 40.dp, maxWidth = 80.dp))
                            Text("Кошелёк",
                                fontSize = 14.sp,
                                color = subTextColor,
                                modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                    Box(contentAlignment = Alignment.TopCenter) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${character.level}", fontSize = 23.sp)
                            Icon(painterResource(R.drawable.arrow_up_icon),
                                tint = Color.White,
                                contentDescription = "levelup",
                                modifier = Modifier.padding(start = 7.dp)
                                    .clip(CircleShape)
                                    .clickable(onClick = levelUp))
                        }
                            Text("Уровень",
                                color = subTextColor,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 4.dp))
                         }
                    }
                    Box(
                        contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 4.dp)) {
                                Icon(painter = painterResource(R.drawable.minus_icon),
                                    contentDescription = "minus",
                                    modifier = Modifier.padding(end = 5.dp)
                                        .clip(CircleShape)
                                        .clickable {decreaseMana()})

                                Text("${character.currentMana}",
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .clickable { onManaClick() }
                                        .background(color = numBack)
                                        .padding(2.dp)
                                        .sizeIn(minWidth = 40.dp, maxWidth = 80.dp))

                                Icon(painter = painterResource(R.drawable.plus_icon),
                                    contentDescription = "plus",
                                    modifier = Modifier.padding(start = 5.dp)
                                        .clip(CircleShape)
                                        .clickable { increaseMana() })

                            }
                            Text("Мана",
                                color = subTextColor,
                                fontSize = 14.sp)
                        }

                    }
                }
            }

        }
        else {
            Row(modifier = Modifier.padding(5.dp)){
                Icon(painter = painterResource(R.drawable.heart_icon),
                    contentDescription = "health icon",
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(9.dp))
                Text("${character.currentHP}")

                Spacer(modifier = Modifier.width(18.dp))

                Icon(painter = painterResource(R.drawable.sheildicon),
                    contentDescription = "shield icon",
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(9.dp))
                Text("${floor(character.armorClass).toInt()}")

                Spacer(modifier = Modifier.width(18.dp))

                Icon(painter = painterResource(R.drawable.speedicon),
                    contentDescription = "speed icon",
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(9.dp))
                Text("${character.speed}")
            }
        }
    }
}


@Composable
fun CharacterStatsCard(character: Character,
                       closeExpandedBox: () -> Unit,
                       openEquipmentLibrary:() -> Unit,
                       openSkillLibrary:() -> Unit) {
    val tabs = listOf("Характеристики", "Навыки", "Инвентарь")

    val scope = rememberCoroutineScope()
    var tabIndex by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = topContainer
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Column(modifier = Modifier.background(color = containerColor)) {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                containerColor = otherContainer,
                contentColor = Color.White,
                divider = {},
                indicator = { },
                edgePadding = 0.dp,
                modifier = Modifier.height(40.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = index == tabIndex,
                        onClick = { scope.launch {
                            tabIndex = index
                            if(index != 0){
                                closeExpandedBox()
                            }
                        } },
                        text = {
                            Text(title, maxLines = 1)
                               },
                        modifier = Modifier
                            .background(
                            color = if (tabIndex == index) containerColor else otherContainer,
                                shape = RoundedCornerShape(
                                    topEnd = cornerRadius,
                                    bottomStart =
                                    if(tabIndex == index - 1)
                                        cornerRadius
                                    else 0.dp,
                                    bottomEnd =
                                    if(tabIndex == index + 1)
                                        cornerRadius
                                    else 0.dp,
                                    topStart = cornerRadius
                                )
                        )
                            //.padding(2.dp)
                    )
                }
            }

            //Cодержимое вкладки
            when (tabIndex) {
                0 -> StatsContent(character)
                1 -> SkillsList(openLibrary = openSkillLibrary)
                2 -> EquipmentList(openLibrary = openEquipmentLibrary)
            }
        }
    }
}

@Composable
fun StatsContent(character: Character) {
    Row(
        modifier = Modifier
            .background(color = containerColor)
            .height(170.dp)
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            Text("Сила", color = Color.White)
            Text("Ловкость", color = Color.White)
            Text("Восприятие", color = Color.White)
            Text("Выносливость", color = Color.White)
        }
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            Text("${character.strength}", color = Color.White,
                modifier = Modifier
                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp))
            Text("${character.dexterity}", color = Color.White,
                modifier = Modifier
                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp))
            Text("${character.perception}", color = Color.White,
                modifier = Modifier
                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp))
            Text("${character.constitution}", color = Color.White,
                modifier = Modifier
                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp))
        }
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            Text("Магия", color = Color.White)
            Text("Интеллект", color = Color.White)
            Text("Харизма", color = Color.White)
            Text("")
        }
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            Text("${character.magic}", color = Color.White,
                modifier = Modifier
                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp))
            Text("${character.intelligence}", color = Color.White,
                modifier = Modifier
                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp))
            Text("${character.charisma}", color = Color.White,
                modifier = Modifier
                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp))
            Text("")
        }
    }
}

@Composable
fun ChangingDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    label: String,
    changingValue: ChangingValues,
    currentValue:Int, maxValue:Int?,
    temporaryValue:Int?
) {
    var increaseValue by remember { mutableStateOf("0") }
    var decreaseValue by remember { mutableStateOf("0") }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = topContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 🔹 Иконка здоровья
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 13.dp)
                    ) {
                        Text(label, color = Color.White, fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.Center))

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            tint = halfAppWhite,
                            modifier = Modifier.clickable(onClick = onDismiss)
                                .align(Alignment.TopEnd)
                        )

                    }


                    Spacer(modifier = Modifier.height(20.dp))

                    // 🔹 Поля для изменения
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row() {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .background(
                                            color = numBack,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currentValue.toString(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                    )
                                }

                                Text(
                                    "Текущие",
                                    color = subTextColor,
                                    modifier = Modifier.padding(top = 4.dp),
                                    fontSize = 13.sp
                                )
                            }
                            if (changingValue != ChangingValues.Gold) {
                                Spacer(Modifier.width(10.dp))
                                Column(modifier = Modifier.padding(end = 10.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .background(
                                                color = numBack,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = maxValue.toString(),
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                        )
                                    }
                                    Text(
                                        "Макс.",
                                        color = subTextColor,
                                        modifier = Modifier.padding(top = 4.dp),
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                                Icon(
                                    painterResource(R.drawable.minus_ic),
                                    contentDescription = "minus",
                                    tint = Color.Red,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                BasicTextField(
                                    value = decreaseValue,
                                    onValueChange = { decreaseValue = it },
                                    modifier = Modifier
                                        .size(height = 30.dp, width = 40.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(numBack)
                                        .padding(2.dp),
                                    singleLine = true,
                                    textStyle = LocalTextStyle.current.copy(
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )

                            Spacer(Modifier.width(10.dp))
                                Icon(
                                    painterResource(R.drawable.plus_ic),
                                    contentDescription = "plus",
                                    tint = Color.Green,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                BasicTextField(
                                    value = increaseValue,
                                    onValueChange = { increaseValue = it },
                                    modifier = Modifier
                                        .size(height = 30.dp, width = 40.dp)
                                        .background(numBack, RoundedCornerShape(8.dp))
                                        .padding(2.dp),
                                    singleLine = true,
                                    textStyle = LocalTextStyle.current.copy(
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    ),
//                                    decorationBox = { innerTextField ->
//                                        if (increaseValue.isBlank()) Text("0", color = Color.LightGray)
//                                        innerTextField()
//                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )

                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    if(changingValue != ChangingValues.Gold) {
                        Button(
                            onClick = {
                                if (maxValue != null) {
                                    onConfirm(maxValue)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = button2)
                        ) {
                            Text("Восстановить до максимума")
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            GradientButton(
                text = "Подтвердить",
                gradient = gradientButton,
                onClick = {
                    val result =
                        currentValue - (if (decreaseValue.isNotBlank()) decreaseValue.toInt() else 0) + (if (increaseValue.isNotBlank()) increaseValue.toInt() else 0)
                    onConfirm(result)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCharacter(//charVM: CharacterViewModel = hiltViewModel(),
                    character: Character,
                    onBackClick: () -> Unit
){
    val context = LocalContext.current
    TopAppBar(
        windowInsets = WindowInsets(0,4,0,0),
        title = {
                Column(modifier = Modifier.fillMaxWidth().
                    padding(end = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Text(
                        character.name,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Text(
                        "${character.classification} ${character.profession1 ?: ""} ${character.race}",
                        fontSize = 16.sp,
                        color = halfAppWhite
                    )

                }
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd){
                val painter = loadImageFromAssets(context, character.imagePath) ?:  rememberVectorPainter(Icons.Default.AccountCircle)
                Image(
                    painter = painter,
                    contentDescription = "View",
                    modifier = Modifier
                        .padding(3.dp)
                        .size(55.dp)
                        .clip(CircleShape)
                        .padding(2.dp)
                )
            }},
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(painter = painterResource(R.drawable.row_up_icon),
                    contentDescription = "IconBack",
                        modifier = Modifier.rotate(-90f),
                        tint = Color.White
                        )
                }
                ) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = topContainer),
        )

}

@Preview
@Composable
fun PrevChar(){
    CPTheme {
        //CharacterView({},

            val character = Character(
                1,
                "Марсиль",
                "Воин",
                "Варвар",
                "Master Fire",
                "Орк",
                level = 20,
                maxHP = 100,
                imagePath = null,
                currentHP = 100,
                gold = 0,
                armorClass = 20f,
                speed = 10,
                maxMana = 500,
                currentMana = 500,
                strength = 10,
                dexterity = 10,
                constitution = 10,
                intelligence = 20,
                magic = 20,
                charisma = 10,
                perception = 20,
                languages = emptyList(),
                initiative = 20
            )
        //ExpandableBox(true, character, {}, {}, {}, {}, {}, {})

        ChangingDialog(
            {},
            onConfirm = {},
            label = "Здоровье",
            changingValue = ChangingValues.Health,
            currentValue = character.currentHP,
            maxValue = character.maxHP,
            temporaryValue = 0
        )

    }

}