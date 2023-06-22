package com.biryulindevelop.unsplash.domain.usecase.implimentations

import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.repository.DigestRemoteRepository
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.GetDigestInfoUseCase
import javax.inject.Inject

class GetDigestInfoUseCaseImpl @Inject constructor(
    private val digestRemoteRepository: DigestRemoteRepository
) : GetDigestInfoUseCase {
    override suspend fun execute(id: String): Digest {
        return digestRemoteRepository.getDigestInfo(id)
    }
}