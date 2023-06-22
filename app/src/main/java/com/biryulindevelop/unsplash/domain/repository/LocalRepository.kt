package com.biryulindevelop.unsplash.domain.repository

import androidx.paging.PagingSource
import com.biryulindevelop.unsplash.data.db.entity.PhotoEntity

interface LocalRepository {

    suspend fun insertData(data: List<PhotoEntity>)

    fun getPagingData(): PagingSource<Int, PhotoEntity>

    suspend fun clear()

    suspend fun setLikeInDataBase(photoEntity: PhotoEntity)

    suspend fun refresh(data: List<PhotoEntity>)
}