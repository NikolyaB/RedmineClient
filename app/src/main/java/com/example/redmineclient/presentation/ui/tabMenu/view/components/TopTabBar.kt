package com.example.redmineclient.presentation.ui.tabMenu.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuItem

@Composable
fun TopTabBar(
    currentTab: TabMenuItem,
    onTabClick: (tab: TabMenuItem) -> Unit
) {
    TopAppBar(
        contentPadding = PaddingValues(),
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp),
    ) {
        TabRow(
            backgroundColor = BlueLight,
            selectedTabIndex = currentTab.ordinal,
            contentColor = BlueDark,
            indicator = {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(it[currentTab.ordinal])
                        .height(4.dp)
                        .padding(horizontal = 4.dp)
                        .background(
                            color = BlueDark,
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
                        Text(
                            title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
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