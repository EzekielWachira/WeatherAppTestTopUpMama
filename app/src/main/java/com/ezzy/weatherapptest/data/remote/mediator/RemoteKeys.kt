package com.ezzy.weatherapptest.data.remote.mediator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val prevPage: Int?,
    val nextPage: Int?,
    val isEndReached: Boolean
)