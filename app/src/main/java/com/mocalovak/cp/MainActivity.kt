package com.mocalovak.cp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mocalovak.cp.data.local.importer.CharacterImporterEntryPoint
import com.mocalovak.cp.data.local.importer.EquipmentImporterEntryPoint
import com.mocalovak.cp.data.local.importer.SkillImporterEntryPoint
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import com.mocalovak.cp.presentation.Scaffold.MainScreen
import com.mocalovak.cp.presentation.nav.AppNavHost
import com.mocalovak.cp.presentation.nav.Screen
import com.mocalovak.cp.presentation.nav.navigateSingleTopTo
import com.mocalovak.cp.ui.theme.CPTheme
import com.mocalovak.cp.ui.theme.topContainer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //импорты с json файлов
        val characterImporter = EntryPointAccessors.fromApplication(
            applicationContext,
            CharacterImporterEntryPoint::class.java
        ).characterImporter()

        val equipmentImporter = EntryPointAccessors.fromApplication(
            applicationContext,
            EquipmentImporterEntryPoint::class.java
        ).equipmentImporter()

        val skillsImporter = EntryPointAccessors.fromApplication(
            applicationContext,
            SkillImporterEntryPoint::class.java
        ).skillImporter()

        lifecycleScope.launch(Dispatchers.IO) {
            characterImporter.importIfNeeded()
            equipmentImporter.importIfNeeded()
            //skillsImporter.importIfNeeded()
        }

        setContent {
            CPTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CPTheme {
        MainScreen()
    }
}