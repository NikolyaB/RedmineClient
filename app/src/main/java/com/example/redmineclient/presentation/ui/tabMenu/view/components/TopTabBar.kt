package com.example.redmineclient.presentation.ui.tabMenu.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuItem

@Composable
fun TopTabBar(
    currentTab: TabMenuItem,
    onTabClick: (tab: TabMenuItem) -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues()
    ) {
        TabRow(
            selectedTabIndex = currentTab.ordinal,
            contentColor = Color.White,
            indicator = {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(it[currentTab.ordinal])
                        .height(4.dp)
                        .padding(horizontal = 4.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp)
                        )
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            TabMenuItem.values().forEachIndexed { index, tab ->
                val title = when (tab) {
                    TabMenuItem.Tasks -> "Задачи"
                    TabMenuItem.TimeEntries -> "Трудозатраты"
                }
                Tab(
                    text = {
                        Text(title)
                    },
                    selected = tab.ordinal == index,
                    onClick = {
                        onTabClick(TabMenuItem.values()[index])
                    },
                )
            }
        }
    }
}