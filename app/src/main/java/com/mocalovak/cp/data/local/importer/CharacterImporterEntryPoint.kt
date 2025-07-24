package com.mocalovak.cp.data.local.importer

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CharacterImporterEntryPoint{
    fun characterImporter():CharacterImporter
}