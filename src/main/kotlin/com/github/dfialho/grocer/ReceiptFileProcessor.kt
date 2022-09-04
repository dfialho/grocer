package com.github.dfialho.grocer

import java.nio.file.Path

interface ReceiptFileProcessor {
    val watchDirectory: Path
    fun onReceiptFile(receiptFile: Path)
}