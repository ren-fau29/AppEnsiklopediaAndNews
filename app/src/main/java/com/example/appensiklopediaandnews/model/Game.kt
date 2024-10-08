package com.example.appensiklopediaandnews.model

data class Game(
    val image: String = "",
    val title: String = "",
    val genre: String = "",
    val description: String = "", // Tambahkan nilai default
    val cpuMi: String = "", // Tambahkan nilai default
    val vgaMi: String = "", // Tambahkan nilai default
    val ramMi: String = "", // Tambahkan nilai default
    val storageMi: String = "", // Tambahkan nilai default
    val cpuRe: String = "", // Tambahkan nilai default
    val vgaRe: String = "", // Tambahkan nilai default
    val ramRe: String = "", // Tambahkan nilai default
    val storageRe: String = "", // Tambahkan nilai default
    val newsUrl: String = "" // Tambahkan nilai default

){
    // Tambahkan constructor tanpa argumen (no-arg constructor)
    constructor() : this(
        image = "",
        title = "",
        genre = "",
        description = "",
        cpuMi = "",
        vgaMi = "",
        ramMi = "",
        storageMi = "",
        cpuRe = "",
        vgaRe = "",
        ramRe = "",
        storageRe = "",
        newsUrl = ""
    )
}
