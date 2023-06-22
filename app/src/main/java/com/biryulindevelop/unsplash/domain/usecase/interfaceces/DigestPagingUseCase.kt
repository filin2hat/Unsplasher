package com.biryulindevelop.unsplash.domain.usecase.interfaceces

import androidx.paging.Pager
import com.biryulindevelop.unsplash.domain.model.Digest

interface DigestPagingUseCase {
    fun execute(): Pager<Int, Digest>
}