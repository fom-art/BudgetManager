package com.wf.bm.feature.settlements.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.feature.settlements.R

@Composable
fun ExpandableFabMenu(
    modifier: Modifier = Modifier,
    goToSettlementsHistory: () -> Unit,
    goToCreateSettlement: () -> Unit,
    openFilterSettlementsDialog: () -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isMenuExpanded, label = "Fab Menu Transition")

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier
            .fillMaxSize()
            .padding(paddingMedium)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
        ) {
            if (isMenuExpanded) {
                transition.AnimatedVisibility(
                    visible = { isMenuExpanded },
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)) + expandVertically(
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500)) + shrinkVertically(
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.history),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            SmallFloatingActionButton(
                                onClick = goToSettlementsHistory,
                            ) {
                                Icon(
                                    Icons.Default.History,
                                    contentDescription = stringResource(R.string.history)
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.add),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            SmallFloatingActionButton(
                                onClick = goToCreateSettlement,
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = stringResource(R.string.add)
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.filter),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            SmallFloatingActionButton(
                                onClick = openFilterSettlementsDialog,
                            ) {
                                Icon(
                                    Icons.Default.FilterList,
                                    contentDescription = stringResource(R.string.filter)
                                )
                            }
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { isMenuExpanded = !isMenuExpanded },
            ) {
                Icon(
                    imageVector = if (isMenuExpanded) Icons.Default.Close else Icons.Default.Menu,
                    contentDescription = stringResource(R.string.toggle_menu)
                )
            }
        }
    }
}