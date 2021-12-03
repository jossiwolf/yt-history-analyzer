package de.jossiwolf.common.util.fs

import androidx.compose.runtime.Composable

@Composable
expect fun FilePicker(onFilePicked: (pickedFileName: String?) -> Unit)
