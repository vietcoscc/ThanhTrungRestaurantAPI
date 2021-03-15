package com.garithanhtrung.controller

import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.FileSystems

@RestController
@RequestMapping("/api/image")
class ImageController {

    @GetMapping("/test")
    fun test(): String {
        return "ThanhTrungRestaurantApplication"
    }

    @GetMapping("/menu")
    fun getMenu() {

    }

    val uploadPath: String = FileSystems.getDefault().getPath("images")
        .toAbsolutePath()
        .toString()

    @RequestMapping(value = ["/images/{name}"], method = [RequestMethod.GET])
    @Throws(IOException::class)
    fun getImage(@PathVariable("name") imgName: String): ResponseEntity<ByteArray?>? {
        try {
            val file = File("$uploadPath/$imgName")
            println(file.path)
            val bytes: ByteArray = StreamUtils.copyToByteArray(file.inputStream())
            return ResponseEntity.ok().body(bytes)
        } catch (ex: Exception) {
            println(ex.message)
        }
        val book = URL("https://images-na.ssl-images-amazon.com/images/I/31M3X330W1L._SX295_BO1,204,203,200_.jpg")
        return ResponseEntity.ok().body(StreamUtils.copyToByteArray(book.openStream()))
    }
}