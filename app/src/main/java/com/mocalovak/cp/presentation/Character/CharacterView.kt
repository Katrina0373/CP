package com.mocalovak.cp.presentation.Character

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.ui.theme.CPTheme
import com.mocalovak.cp.ui.theme.button2
import com.mocalovak.cp.ui.theme.containerColor
import com.mocalovak.cp.ui.theme.gradientButton
import com.mocalovak.cp.ui.theme.hptems
import com.mocalovak.cp.ui.theme.numBack
import com.mocalovak.cp.ui.theme.otherContainer
import com.mocalovak.cp.ui.theme.subTextColor
import com.mocalovak.cp.ui.theme.topContainer
import com.mocalovak.cp.utils.GradientButton
import kotlinx.coroutines.launch
import kotlin.math.exp

val cornerRadius = 14.dp
val gradientColors = listOf(Color(0xFF3103AF), Color(0xFF9805B6))

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


    Scaffold(topBar = {TopBarCharacter( character)}, modifier = Modifier.fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(5.dp).padding(padding)) {
            ExpandableBox(character = character)
            Spacer(modifier = Modifier.height(5.dp))
            CharacterStatsCard(character)
            Box(modifier = Modifier.weight(1f)) {
                Text("Языки:" )
            }

            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter) {

                Row(modifier = Modifier.fillMaxWidth()
                    .padding(10.dp).height(40.dp),
                    horizontalArrangement = Arrangement.Center) {

                    Button(onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = button2),
                        shape = RoundedCornerShape(cornerRadius),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text("Проверка")
                            Spacer(Modifier.width(5.dp))
                            Icon(painter = painterResource(R.drawable.d20_icon), contentDescription = "d20")
                        }
                    }

                    GradientButton(
                        gradientColors = gradientColors,
                        cornerRadius = cornerRadius,
                        roundedCornerShape = RoundedCornerShape(cornerRadius),
                        hasRipple = false,
                        onClick = {},
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        contentButton = {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                Text("Атака")
                                Spacer(Modifier.width(5.dp))
                                Icon(painter = painterResource(R.drawable.swords_icon), contentDescription = "d20")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableBox(
    modifier: Modifier = Modifier,
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
            .fillMaxWidth()
            .background(color = topContainer, shape = RoundedCornerShape(8.dp))
            .padding(5.dp)

    ) {

        if(expanded){
            Column(modifier = Modifier.padding(13.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly) {

                    Box(contentAlignment = Alignment.Center){
                        Image(painterResource(R.drawable.heart_icon),
                            contentDescription = "background",
                            modifier = Modifier.padding(top = 5.dp))

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
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    Box(contentAlignment = Alignment.Center){
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Text("${character.currentHP}",
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
                    Box(contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${character.armorClass}")
                            Icon(painterResource(R.drawable.arrow_up_icon),
                                contentDescription = "levelup",
                                modifier = Modifier.padding(start = 3.dp))
                        }
                            Text("Уровень")
                         }
                    }
                    Box() {
                        Text("${character.speed}") }
                }
            }
            Box(contentAlignment = Alignment.TopEnd,
                modifier = Modifier.fillMaxWidth()){
                IconButton(
                    onClick = { expanded = !expanded }
                ) { Icon(
                    painter = painterResource(R.drawable.row_up_icon),
                    contentDescription = "row",
                    modifier = Modifier.rotate(rotationState)
                ) }
            }
        }
        else{

        }
    }
}


@Composable
fun CharacterStatsCard(character: Character) {
    val tabs = listOf("Характеристики", "Навыки", "Инвентарь")

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {tabs.size}
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = topContainer
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Column() {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = topContainer,
                contentColor = Color.White,
                divider = {},
                indicator = { },
                edgePadding = 0.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = { scope.launch {
                            pagerState.animateScrollToPage(index)
                        } },
                        text = {
                            Text(title, maxLines = 1)
                               },
                        modifier = Modifier
                            .background(
                            color = if (pagerState.currentPage == index) containerColor else otherContainer
                        )
                            .padding(6.dp)
                    )
                }
            }

            // 🔹 Содержимое вкладки
            when (pagerState.currentPage) {
                0 -> StatsContent(character)
                1 -> Text("Навыки", modifier = Modifier.padding(16.dp), color = Color.White)
                2 -> Text("Инвентарь", modifier = Modifier.padding(16.dp), color = Color.White)
            }
        }
    }
}

@Composable
fun StatsContent(character: Character) {
    Row(
        modifier = Modifier
            .background(color = containerColor)
            .height(200.dp)
            .padding(10.dp)
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
            Text("${character.wisdom}", color = Color.White,
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
        }
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            Text("${character.charisma}", color = Color.White,
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
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCharacter(//charVM: CharacterViewModel = hiltViewModel(),
                    character: Character
){

    TopAppBar(
        title = {
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(character.name, color = Color.White)
                Text("${character.classification} ${character.profession1 ?: ""} ${character.race}",
                    )
            }},
        navigationIcon = {
            IconButton(
                onClick = {},
                content = {Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "IconBack")}
                ) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = topContainer)
        )

}

@Preview
@Composable
fun PrevChar(){
    CPTheme {
        ExpandableBox(
            character = Character(
            "char001",
            "Llos",
            "Warrior",
            "Mage Fire",
            "Master Fire",
            "god",
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
                wisdom = 20,
                charisma = 10,
        ))
    }
}