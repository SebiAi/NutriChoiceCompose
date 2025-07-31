package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.FilterState
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme
import kotlinx.coroutines.launch

enum class MultiSelectFilterSetting {
    HIGH_PROTEIN, LOW_FAT, ECO_FRIENDLY, HEALTHY, VEGETARIAN, VEGAN, COST_EFFICIENT
}
enum class SingleSelectFilterSetting {
    CARBS, CALORIES
}

private data class MultiSelectFilterSettingData(
    val text: String,
    val selected: Boolean,
    val multiSelectFilterSetting: MultiSelectFilterSetting
)
private data class SingleSelectFilterSettingData(
    val textLow: String,
    val textHigh: String,
    val selected: FilterState.ThreeStateFilterState,
    val singleSelectFilterSetting: SingleSelectFilterSetting
)

private data class SingeSelectButtonData(
    val label: String,
    val buttonType: FilterState.ThreeStateFilterState
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,

    initialFilterState: FilterState,
    filterPreferences: FilterPreferences,
    onSaveFilter: (FilterState) -> Unit,

    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    val scope = rememberCoroutineScope()
    var filterState by rememberSaveable { mutableStateOf(initialFilterState) }

    val onMultiSelectFilterStateChanged: (MultiSelectFilterSetting, Boolean) -> Unit = { multiSelectFilterSetting, newState ->
        filterState = when (multiSelectFilterSetting) {
            MultiSelectFilterSetting.HIGH_PROTEIN -> filterState.copy(highProtein = newState)
            MultiSelectFilterSetting.LOW_FAT -> filterState.copy(lowFat = newState)
            MultiSelectFilterSetting.ECO_FRIENDLY -> filterState.copy(ecoFriendly = newState)
            MultiSelectFilterSetting.HEALTHY -> filterState.copy(healthy = newState)
            MultiSelectFilterSetting.VEGETARIAN -> filterState.copy(vegetarian = newState)
            MultiSelectFilterSetting.VEGAN -> filterState.copy(vegan = newState)
            MultiSelectFilterSetting.COST_EFFICIENT -> filterState.copy(costEfficient = newState)
        }
    }
    val onSingleSelectFilterStateChanged: (SingleSelectFilterSetting, FilterState.ThreeStateFilterState) -> Unit =  { singleSelectFilterSetting, newState ->
        filterState = when (singleSelectFilterSetting) {
            SingleSelectFilterSetting.CARBS -> filterState.copy(carbs = newState)
            SingleSelectFilterSetting.CALORIES -> filterState.copy(calories = newState)
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.padding(12.dp, 0.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.filter_heading),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = stringResource(R.string.filter_multi_select_heading),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(12.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1.4F),
                    horizontalAlignment = Alignment.Start
                ) {
                    listOf(
                        MultiSelectFilterSettingData(
                            text = stringResource(R.string.high_protein),
                            selected = filterState.highProtein,
                            multiSelectFilterSetting = MultiSelectFilterSetting.HIGH_PROTEIN
                        ),
                        MultiSelectFilterSettingData(
                            text = stringResource(R.string.preferences_chip_eco_friendly),
                            selected = filterState.ecoFriendly,
                            multiSelectFilterSetting = MultiSelectFilterSetting.ECO_FRIENDLY
                        ),
                        MultiSelectFilterSettingData(
                            text = stringResource(R.string.preferences_chip_vegetarian),
                            selected = filterState.vegetarian,
                            multiSelectFilterSetting = MultiSelectFilterSetting.VEGETARIAN
                        ),
                        MultiSelectFilterSettingData(
                            text = stringResource(R.string.cost_efficient),
                            selected = filterState.costEfficient,
                            multiSelectFilterSetting = MultiSelectFilterSetting.COST_EFFICIENT
                        )
                    ).forEach { filter ->
                        MyFilterChip(filter, onMultiSelectFilterStateChanged)
                    }
                }
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.Start
                ) {
                    listOf(
                        MultiSelectFilterSettingData(
                            text = stringResource(R.string.low_fat),
                            selected = filterState.lowFat,
                            multiSelectFilterSetting = MultiSelectFilterSetting.LOW_FAT

                        ),
                        MultiSelectFilterSettingData(
                            text = stringResource(R.string.preferences_chip_healthy),
                            selected = filterState.healthy,
                            multiSelectFilterSetting = MultiSelectFilterSetting.HEALTHY
                        ),
                        MultiSelectFilterSettingData(
                            text = stringResource(R.string.preferences_chip_vegan),
                            selected = filterState.vegan,
                            multiSelectFilterSetting = MultiSelectFilterSetting.VEGAN
                        )
                    ).forEach { filter ->
                        MyFilterChip(filter, onMultiSelectFilterStateChanged)
                    }
                }
            }

            Text(
                style = MaterialTheme.typography.titleSmall,
                text = stringResource(R.string.filter_single_select_heading),
                fontWeight = FontWeight.Bold
            )
            Column(
                modifier = Modifier.padding(12.dp, 0.dp)
            ) {
                listOf(
                    SingleSelectFilterSettingData(
                        textLow = stringResource(R.string.low_carbs),
                        textHigh = stringResource(R.string.high_carbs),
                        selected = filterState.carbs,
                        singleSelectFilterSetting = SingleSelectFilterSetting.CARBS
                    ),
                    SingleSelectFilterSettingData(
                        textLow = stringResource(R.string.low_calories),
                        textHigh = stringResource(R.string.high_calories),
                        selected = filterState.calories,
                        singleSelectFilterSetting = SingleSelectFilterSetting.CALORIES
                    )
                ).forEach { filter ->
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val buttons = listOf(
                            SingeSelectButtonData(
                                label = filter.textLow,
                                buttonType = FilterState.ThreeStateFilterState.LOW
                            ),
                            SingeSelectButtonData(
                                label = stringResource(R.string.filter_single_select_neutral_position_label),
                                buttonType = FilterState.ThreeStateFilterState.NEUTRAL
                            ),
                            SingeSelectButtonData(
                                label = filter.textHigh,
                                buttonType = FilterState.ThreeStateFilterState.HIGH
                            )
                        )
                        buttons.forEachIndexed { index, buttonData ->
                            SegmentedButton(
                                selected = filter.selected == buttonData.buttonType,
                                onClick = { onSingleSelectFilterStateChanged(filter.singleSelectFilterSetting, buttonData.buttonType) },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = buttons.size
                                ),
                                icon = {}
                            ) {
                                Text(
                                    text = buttonData.label
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        filterState = FilterState(filterPreferences)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.button_reset)
                    )
                }
                Button(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissRequest()
                            }
                        }
                        onSaveFilter(filterState)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.button_apply)
                    )
                }
            }
        }
    }
}

@Composable
private fun MyFilterChip(
    multiSelectFilterSettingData: MultiSelectFilterSettingData,
    onFilterStateChanged: (multiSelectFilterSetting: MultiSelectFilterSetting, newState: Boolean) -> Unit
) {
    FilterChip(
        onClick = {
            onFilterStateChanged(
                multiSelectFilterSettingData.multiSelectFilterSetting,
                !multiSelectFilterSettingData.selected
            )
        },
        label = {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = multiSelectFilterSettingData.text
            )
        },
        selected = multiSelectFilterSettingData.selected,
        leadingIcon = {
            Icon(
                imageVector = if (multiSelectFilterSettingData.selected) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
                contentDescription = null
            )
        }
    )
}

@PreviewLightDark
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBottomSheetPreview() {
    NutriChoiceComposeTheme {
        FilterBottomSheet(
            modifier = Modifier.fillMaxSize(),

            initialFilterState = FilterState(
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                FilterState.ThreeStateFilterState.LOW,
                FilterState.ThreeStateFilterState.NEUTRAL
            ),
            filterPreferences = FilterPreferences(),

            onSaveFilter = {},
            onDismissRequest = {},

            // Use rememberModalBottomSheetState for real implementations
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                density = LocalDensity.current,
                initialValue = SheetValue.Expanded
            )
        )
    }
}