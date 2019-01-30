package com.example.newscops.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourceModel (
    @PrimaryKey var id: String,
    var name: String? = "",
    var description: String? = "",
    var url: String? = "",
    var category: String? = "",
    var language: String? = "",
    var country: String? = ""
)