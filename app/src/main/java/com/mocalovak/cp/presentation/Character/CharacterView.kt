package com.mocalovak.cp.presentation.Character

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.ui.theme.CPTheme
import com.mocalovak.cp.ui.theme.backColor
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.gradientButton
import com.mocalovak.cp.ui.theme.hptems
import com.mocalovak.cp.ui.theme.numBack
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.subTextColor
import com.mocalovak.cp.ui.theme.topContainer
import com.mocalovak.cp.utils.loadImageFromAssets
import kotlinx.coroutines.launch
import kotlin.math.exp

val cornerRadius = 14.dp

@Composable
fun CharacterScreen(charVM: CharacterViewModel = hiltViewModel(),
                    characterId: String,
                    onBackClick: () -> Unit){
    val character by charVM.character.collectAsState()

    when {
        character == null -> CircularProgressIndicator()
        else -> CharacterView(onBackClick, character!!)
    }
}

@Composable
fun CharacterView(
    onBackClick: () -> Unit,
    //charVM: CharacterViewModel = hiltViewModel(),
    character: Character
    ){


    Scaffold(topBar = {TopBarCharacter(character, onBackClick)}, modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .padding(top = padding.calculateTopPadding(), bottom = 5.dp),
            ) {

            var isCommonInfoBoxExpanded by remember { mutableStateOf(true) }

            ExpandableBox(character = character, isExpanded = isCommonInfoBoxExpanded)

            CharacterStatsCard(character) {isCommonInfoBoxExpanded = false}

            Box(contentAlignment = Alignment.TopStart,
                modifier = Modifier.fillMaxWidth()
                    .padding(7.dp)
                    .background(color = containerColor,
                        shape = RoundedCornerShape(cornerRadius)
                    )
                    .padding(vertical = 13.dp, horizontal = 20.dp)) {
                Text("Языки:" )
            }
            BottomActionButtons(onCheckClick = {

            },
                onAttackClick = {

                })

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
    character: Character
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
                                .clickable {  })

                        Text(text = "${character.currentHP}", fontSize = 20.sp)
                    }

                    Box(contentAlignment = Alignment.Center){
                        Image(painterResource(R.drawable.sheildicon), contentDescription = "background")
                        Text(text = "${character.armorClass}", fontSize = 20.sp)
                    }

                    Box(contentAlignment = Alignment.Center){
                        Image(painterResource(R.drawable.speedicon),
                            contentDescription = "background",
                            modifier = Modifier.padding(top = 3.dp))
                        Text(text = "${character.speed}", fontSize = 20.sp,
                            modifier = Modifier.padding(end = 12.dp))
                    }
                }
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(start = 37.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top) {
                    Box(contentAlignment = Alignment.Center){
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Text("${character.gold}",
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                modifier = Modifier.background(color = numBack,
                                    shape = RoundedCornerShape(8.dp))
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
                                    .clickable(onClick = {}))
                        }
                            Text("Уровень",
                                color = subTextColor,
                                fontSize = 14.sp)
                         }
                    }
                    Box(
                        contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 4.dp)) {
                                Icon(painter = painterResource(R.drawable.minus_icon),
                                    contentDescription = "minus",
                                    modifier = Modifier.padding(end = 5.dp))

                                Text("${character.currentMana}",
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    modifier = Modifier.background(color = numBack,
                                        shape = RoundedCornerShape(8.dp))
                                        .padding(2.dp)
                                        .sizeIn(minWidth = 40.dp, maxWidth = 80.dp))

                                Icon(painter = painterResource(R.drawable.plus_icon),
                                    contentDescription = "plus",
                                    modifier = Modifier.padding(start = 5.dp))

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
                Text("${character.armorClass}")

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
fun CharacterStatsCard(character: Character, closeExpanedeBox: () -> Unit) {
    val tabs = listOf("Характеристики", "Навыки", "Инвентарь")

    val scope = rememberCoroutineScope()
    var tabIndex by remember { mutableStateOf(0) }

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
                                closeExpanedeBox
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
                1 -> SkillsList()
                2 -> EquipmentList()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    character: Character
) {
    var increaseValue by remember { mutableStateOf("0") }
    var decreaseValue by remember { mutableStateOf("0") }
    Dialog(onDismissRequest = onDismiss) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = topContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 🔹 Иконка здоровья
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.heart_icon), // твоя иконка сердца
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )

                        Text("Здоровье", color = Color.White, fontSize = 18.sp)

                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Закрыть",
                                tint = Color.White,
                                modifier = Modifier.clickable(onClick = onDismiss)
                            )

                    }


                    Spacer(modifier = Modifier.height(12.dp))

                    // 🔹 Поля для изменения
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 23.dp)) {
                            Text(
                                "${character.currentHP}/${character.maxHP} ", color = Color.White,
                                modifier = Modifier
                                    //.height(30.dp)
                                    .background(color = numBack, shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp),
                                textAlign = TextAlign.Center
                            )
                            Text("Текущие",
                                color = subTextColor,
                                modifier = Modifier.padding(top = 4.dp))
                        }

                        Box() {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painterResource(R.drawable.minus_ic),
                                    contentDescription = "minus",
                                    tint = Color.Red,
                                    modifier = Modifier.padding(end = 9.dp)
                                )
                                OutlinedTextField(
                                    value = decreaseValue,
                                    onValueChange = { newValue ->
                                        decreaseValue = newValue
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.size(width = 30.dp, height = 30.dp)
                                        .background(
                                            color = numBack,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    maxLines = 1,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent
                                    ),
                                    placeholder = { Text(decreaseValue) },

                                    )
                            }
                        }

                        Box(contentAlignment = Alignment.Center) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painterResource(R.drawable.plus_ic),
                                    contentDescription = "plus",
                                    tint = Color.Green,
                                    modifier = Modifier.padding(end = 9.dp)
                                )
                                OutlinedTextField(
                                    value = increaseValue,
                                    onValueChange = { newValue ->
                                        increaseValue = newValue
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.size(width = 30.dp, height = 30.dp)
                                        .background(
                                            color = numBack,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    maxLines = 1,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent
                                    ),
                                    placeholder = { Text(increaseValue) },

                                    )
                            }
                        }
                    }

                }
            }
            Spacer(Modifier.height(10.dp))
            // 🔹 Кнопка Подтвердить
            GradientButton(
                text = "Подтвердить",
                gradient = gradientButton,
                onClick = onConfirm,
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
                        fontSize = 16.sp
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

@Preview(showBackground = true)
@Composable
fun PrevChar(){
    CPTheme {
        CharacterView({},
            character = Character(
            "char001",
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
                armorClass = 20,
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
                languages = emptyList()
        ))
    }
}