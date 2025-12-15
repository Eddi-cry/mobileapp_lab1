package com.example.noteapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ThemeSelection(
    currentTheme: String,
    onThemeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ThemeOption(
            themeCode = "light",
            themeName = "Светлая",
            icon = Icons.Default.LightMode,
            isSelected = currentTheme == "light",
            onClick = { onThemeSelected("light") }
        )
        ThemeOption(
            themeCode = "system",
            themeName = "Системная",
            icon = Icons.Default.Settings,
            isSelected = currentTheme == "system",
            onClick = { onThemeSelected("system") }
        )
        ThemeOption(
            themeCode = "dark",
            themeName = "Тёмная",
            icon = Icons.Default.DarkMode,
            isSelected = currentTheme == "dark",
            onClick = { onThemeSelected("dark") }
        )
    }
}

@Composable
fun ThemeOption(
    themeCode: String,
    themeName: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = if (isSelected) {
            CardDefaults.cardElevation(defaultElevation = 4.dp)
        } else {
            CardDefaults.cardElevation(defaultElevation = 1.dp)
        }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = themeName,
                modifier = Modifier.size(32.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = themeName,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}