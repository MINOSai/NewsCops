package com.example.newscops.model.api

import com.example.newscops.model.SourceModel

data class SourceResponse (
    var status: String,
    var sources: List<SourceModel>
)