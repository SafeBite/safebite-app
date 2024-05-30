package com.celvine.deb.esail.bby.di

import com.celvine.deb.esail.bby.data.repositories.FoodRepo

object Injection {
    fun provideFoodRepository(): FoodRepo {
        return FoodRepo()
    }
}