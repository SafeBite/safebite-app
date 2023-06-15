package com.celvine.deb.esail.bby.data.sources

import com.celvine.deb.esail.bby.R
import com.celvine.deb.esail.bby.data.model.ProfileItemModel

object ProfileItem {
    val data = listOf<ProfileItemModel>(
        ProfileItemModel(
            Id = 1,
            Icon = R.drawable.captain,
            Label = "Change Allergen",
            Route = "/"
        ),
        ProfileItemModel(
            Id = 2,
            Icon = R.drawable.earth_asia,
            Label = "Language",
            Route = "/"
        )
    )
}