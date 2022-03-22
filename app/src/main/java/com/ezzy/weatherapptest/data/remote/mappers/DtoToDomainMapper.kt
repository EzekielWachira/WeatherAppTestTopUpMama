package com.ezzy.weatherapptest.data.remote.mappers

interface DtoToDomainMapper<E, V> {
    fun toDomain(dto: E): V
}