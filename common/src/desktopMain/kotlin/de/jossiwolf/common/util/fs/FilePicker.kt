package de.jossiwolf.common.util.fs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
actual fun FilePicker(onFilePicked: (pickedAbsoluteFilePath: String?) -> Unit) {
    FileDialog(
        onCloseRequest = { pickedFile -> onFilePicked(pickedFile?.absolutePath) }
    )
}

@Composable
private fun FileDialog(
    parent: Frame? = null,
    onCloseRequest: (file: File?) -> Unit,
    title: String = "Choose a file",
) = AwtWindow(
    create = {
        object : FileDialog(parent, title, LOAD) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    val fileName = this.file
                    val fileDirectory = this.directory
                    val file = File(fileDirectory, fileName)
                    onCloseRequest(file)
                }
            }
        }
    },
    dispose = FileDialog::dispose
)
