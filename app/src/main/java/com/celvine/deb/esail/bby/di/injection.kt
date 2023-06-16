package com.celvine.deb.esail.bby.di

//import com.celvine.deb.esail.bby.data.repositories.*
import com.celvine.deb.esail.bby.presentation.repositories.CartRepository
import com.celvine.deb.esail.bby.presentation.repositories.ContentRepository
import com.celvine.deb.esail.bby.presentation.repositories.CoursesRepository
import com.celvine.deb.esail.bby.presentation.repositories.DetailRepository
import com.celvine.deb.esail.bby.presentation.repositories.MyCoursesRepository
import com.celvine.deb.esail.bby.presentation.repositories.WishlistRepository

object Injection {
    fun provideCourseRepository(): CoursesRepository {
        return CoursesRepository.getInstance()
    }

    fun provideContentRepository(): ContentRepository {
        return ContentRepository.getInstance()
    }

    fun provideWishlistRepository(): WishlistRepository {
        return WishlistRepository.getInstance()
    }

    fun provideDetailRepository(): DetailRepository {
        return DetailRepository.getInstance()
    }

    fun provideCartRepository(): CartRepository {
        return CartRepository.getInstance()
    }

    fun provideMyCoursesRepository(): MyCoursesRepository {
        return MyCoursesRepository.getInstance()
    }
}