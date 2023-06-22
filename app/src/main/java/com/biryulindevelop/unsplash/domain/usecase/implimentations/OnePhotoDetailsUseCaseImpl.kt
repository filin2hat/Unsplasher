package com.biryulindevelop.unsplash.domain.usecase.implimentations

import com.biryulindevelop.unsplash.domain.model.PhotoDetails
import com.biryulindevelop.unsplash.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.OnePhotoDetailsUseCase
import javax.inject.Inject

class OnePhotoDetailsUseCaseImpl @Inject constructor(
    private val repository: PhotoRemoteRepository
) : OnePhotoDetailsUseCase {
    override suspend fun execute(id: String): PhotoDetails {
        return repository.getPhotoDetails(id).toPhotoDetails()
    }
}