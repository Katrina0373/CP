package com.mocalovak.cp.presentation.HomePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocalovak.cp.R
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.utils.CharacterItem
import com.mocalovak.cp.presentation.HomePage.components.TelegramRef
import com.mocalovak.cp.presentation.HomePage.components.ContentWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(viewModel: HomePageViewModel = hiltViewModel(),  onShowAllClick: () -> Unit, onShowCharClick: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is HomePageUiState.Loading -> CircularProgressIndicator()
        is HomePageUiState.Success -> HomePage((uiState as HomePageUiState.Success).characters,
            onShowAllClick, onShowCharClick)
        is HomePageUiState.Error -> HomePage(emptyList(), onShowAllClick, onShowCharClick)
    }
}

@Composable
fun HomePage(characters: List<Character>, onShowAllClick: () -> Unit, onShowCharClick: (Int) -> Unit,
             viewModel: HomePageViewModel = hiltViewModel()
){

    Column(modifier = Modifier.fillMaxSize()
        .padding(8.dp)) {
        //SearchItem()
            if(characters.isNotEmpty()) {
                Text("Мои персонажи", modifier = Modifier.padding(5.dp))
                CharacterItem(characters[0]) {
                    viewModel.updateLastCharacter(characters[0].id)
                    onShowCharClick(characters[0].id) }
                if(characters.size > 1){
                    CharacterItem(characters[1]) {
                        viewModel.updateLastCharacter(characters[1].id)
                        onShowCharClick(characters[1].id) }
                    TextButton(onClick = {
                        viewModel.removeLastCharacterId()
                        onShowAllClick()
                    }) {
                        Text(
                            "Смотреть все",
                            textAlign = TextAlign.Right)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
                else {
                    Spacer(modifier = Modifier.height(130.dp))
                }
            }
            else {
                Text("Персонажи не найдены")
                Spacer(modifier = Modifier.height(200.dp))
            }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ContentWidget(
                    picture = painterResource(R.drawable.heroes),
                    title = "Книга правил",
                    description = "Общие понятия, описания рас и классов и прочее",
                    buttonText = "Читать"
                )
            }
            item {
                ContentWidget(
                    picture = painterResource(R.drawable.cool_mage),
                    title = "Навыки",
                    description = "Общие, магические, боевые, расовые",
                    buttonText = "Искать"
                )
            }
            item {
                ContentWidget(
                    picture = painterResource(R.drawable.war_hammer),
                    title = "Инвентарь",
                    description = "Броня, артефакты, оружие",
                    buttonText = "Искать"
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(10.dp),
            contentAlignment = Alignment.BottomCenter) {
            TelegramRef()
        }
    }
}

@Composable
fun ScrollableCardRow() {
    val listState = rememberLazyListState()

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                val firstVisible = listState.firstVisibleItemIndex
                if (firstVisible > 0) {
                    CoroutineScope(Dispatchers.Main).launch {
                        listState.animateScrollToItem(firstVisible - 1)
                    }
                }
            }
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Left")
        }

        LazyRow(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ContentWidget(
                    picture = painterResource(R.drawable.ic_launcher_foreground),
                    title = "Книга правил",
                    description = "Общие понятия, описания рас и классов и прочее",
                    buttonText = "Читать"
                )
            }
            item {
                ContentWidget(
                    picture = painterResource(R.drawable.ic_launcher_foreground),
                    title = "Навыки",
                    description = "Общие, магические, боевые, расовые",
                    buttonText = "Искать"
                )
            }
            item {
                ContentWidget(
                    picture = painterResource(R.drawable.ic_launcher_foreground),
                    title = "Инвентарь",
                    description = "Броня, артефакты, оружие",
                    buttonText = "Искать"
                )
            }
        }

        IconButton(
            onClick = {
                val firstVisible = listState.firstVisibleItemIndex
                CoroutineScope(Dispatchers.Main).launch {
                    listState.animateScrollToItem(firstVisible + 1)
                }
            }
        ) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Right")
        }
    }
}


@Preview
@Composable
fun PrevHome(){
    //HomePage(emptyList())
}