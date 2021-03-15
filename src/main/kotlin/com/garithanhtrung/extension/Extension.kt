package com.garithanhtrung.extension

import org.imgscalr.Scalr
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.FileSystems
import java.util.*
import javax.imageio.ImageIO


fun MultipartFile.saveToDisk(): String? {

    val uploadPath: String = FileSystems.getDefault().getPath("images")
        .toAbsolutePath()
        .toString()

    val folder = File(uploadPath)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    val savedName = System.currentTimeMillis().toString() + "_" + this.originalFilename
    val originFile = File("$uploadPath/$savedName")
    val thumbnailFile = File("$uploadPath/thumb_$savedName")
    val originImage = Scalr.resize(ImageIO.read(this.inputStream), Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, 900)
    val thumbnailImage = Scalr.resize(ImageIO.read(this.inputStream), Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, 300)
    val type = this.originalFilename?.getExtension()?.get()
    ImageIO.write(originImage, type, originFile)
    ImageIO.write(thumbnailImage, type, thumbnailFile)
    return savedName
}

fun String.getExtension(): Optional<String> {
    return Optional.ofNullable(this)
        .filter { f -> f.contains(".") }
        .map { f -> f.substring(this.lastIndexOf(".") + 1) }
}