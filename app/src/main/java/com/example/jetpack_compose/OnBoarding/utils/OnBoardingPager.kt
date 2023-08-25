package com.example.jetpack_compose.OnBoarding.utils

import androidx.annotation.DrawableRes
import com.example.jetpack_compose.R


sealed class OnBoardingPager(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPager(
        image = R.drawable.food,
        title = "Order For Food",
        description = "Satisfy cravings effortlessly. Browse, customize, and enjoy diverse cuisines." +
                " Simple, convenient, and delightful food ordering experience, tailored for you."
    )

    object Second : OnBoardingPager(
        image = R.drawable.delivery,
        title = "Fast Delivery",
        description ="Speedy doorstep deliveries. Get your favorite food piping hot and fresh, " +
                "quicker than ever. Enjoy convenience without compromising quality."
    )

    object Third : OnBoardingPager(
        image = R.drawable.tracking,
        title = "Order Tracking",
        description ="Know your order's journey. Real-time tracking lets you follow your food from kitchen to door, " +
                "ensuring you're always in the loop."
    )
}
