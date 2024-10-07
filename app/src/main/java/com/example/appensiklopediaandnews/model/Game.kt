package com.example.appensiklopediaandnews.model

data class Game(
    val image: String = "",
    val title: String = "",
    val genre: String = "",
    val description: String, // Tambahkan field description
    val cpuMi: String, // Tambahkan field cpuMi
    val vgaMi: String, // Tambahkan field vgaMi
    val ramMi: String, // Tambahkan field ramMi
    val storageMi: String, // Tambahkan field storageMi
    val cpuRe: String, // Tambahkan field cpuRe
    val vgaRe: String, // Tambahkan field vgaRe
    val ramRe: String, // Tambahkan field ramRe
    val storageRe: String, // Tambahkan field storageRe
    val newsUrl: String, // Tambahkan field newsUrl

)
