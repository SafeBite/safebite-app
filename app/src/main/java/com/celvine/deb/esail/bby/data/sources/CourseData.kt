package com.celvine.deb.esail.bby.data.sources

import com.celvine.deb.esail.bby.data.model.CaptainModel
import com.celvine.deb.esail.bby.data.model.CourseModel

object CourseData {
    val data = listOf(
        CourseModel(
            id = 1,
            title = "Pie Susu",
            sortDesc = "Learn Docker, Docker Compose, Multi-Container Projects, Deployment and all about Kubernetes from the ground up!",
            desc = "Pie susu, also known as Balinese milk pie, is a traditional Indonesian dessert that originated in Bali. It is a sweet and creamy custard-filled pastry that is popular among locals and tourists alike.\n",
            isFree = false,
            price = 129000,
            banner = "https://img-global.cpcdn.com/recipes/90ccc6ae763aa9f1/1200x630cq70/photo.jpg",
            rating = "4.7 (14k)",
            Captain = CaptainModel(
                Name = "Milk",
                Job = "Online Education",
                Image = "https://img-c.udemycdn.com/user/200_H/31926668_94e7_6.jpg"
            ),
            category = "You Can't Eat This Food!",
            isPopular = true,
            isFlashSale = true,
            totalTime = "10h 44m",
            totalVideo = 101
        ),
        CourseModel(
            id = 2,
            title = "Milkshake",
            sortDesc = "Belajar pemrograman Go-Lang dari pemula sampai mahir disertai studi kasus. Materi akan selalu di-update secara berkala",
            desc = "A milkshake is a sweet and creamy beverage made by blending milk, ice cream, and flavorings. It is a popular treat enjoyed around the world, often served in diners, ice cream parlors, or fast-food restaurants.",
            isFree = false,
            price = 340000,
            banner = "https://hips.hearstapps.com/hmg-prod/images/delish-220524-chocolate-milkshake-001-ab-web-1654180529.jpg?crop=0.647xw:0.972xh;0.177xw,0.0123xh&resize=1200:*",
            rating = "4.8 (2k)",
            Captain = CaptainModel(
                Name = "Milk",
                Job = "Technical Architect and Content Creator",
                Image = "https://img-c.udemycdn.com/user/200_H/15481646_a97d_5.jpg"
            ),
            category = "You Can't Eat This Food!",
            isPopular = true,
            isFlashSale = true,
            totalTime = "09h 22m",
            totalVideo = 210
        ),
        CourseModel(
            id = 3,
            title = "Milk",
            sortDesc = "Build a To-Do App using Modern Declarative UI Toolkit called Jetpack Compose to Accelerate your UI and App development.",
            desc = "Milk is a nutrient-rich liquid that is produced by mammals, including humans. It is typically white or slightly off-white in color and is consumed as a beverage or used as an ingredient in various food preparations.",
            isFree = false,
            price = 700000,
            banner = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/004-soymilk.jpg/1200px-004-soymilk.jpg",
            rating = "4.5 (399)",
            Captain = CaptainModel(
                Name = "Milk",
                Job = "Android Developer/Designer",
                Image = "https://img-c.udemycdn.com/user/200_H/34894726_5662_3.jpg"
            ),
            category = "You Can't Eat This Food!",
            isPopular = false,
            isFlashSale = true,
            totalTime = "9h 16m",
            totalVideo = 62
        ),
        CourseModel(
            id = 4,
            title = "Chia Pudding",
            sortDesc = "Saatnya menjadi Android Expert dengan belajar Clean Architecture, Reactive, Dependency Injection, Modularization, Performance, dan Security.",
            desc = "Chia pudding is a nutritious and popular dish made from chia seeds and a liquid base such as milk, plant-based milk, or fruit juice. Chia seeds are small, nutrient-dense seeds that come from the Salvia hispanica plant, which is native to Mexico and Guatemala.",
            isFree = false,
            price = 1500000,
            banner = "https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2020/01/Chia-Pudding-main.jpg",
            rating = "4.8 (3432)",
            Captain = CaptainModel(
                Name = "Milk",
                Job = "Online Academy",
                Image = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/new-ui-logo.png"
            ),
            category = "You Can't Eat This Food",
            isPopular = true,
            isFlashSale = true,
            totalTime = "90h",
            totalVideo = 0
        ),
        CourseModel(
            id = 5,
            title = "Cappuccino",
            sortDesc = "Pada kelas kali ini kita akan bahas tuntas tentang pengertian dan juga penggunaan grid system pada design website menggunakan software Figma.com",
            desc = "Cappuccino is a popular espresso-based coffee beverage that originated in Italy. It consists of equal parts of espresso, steamed milk, and milk foam.",
            isFree = true,
            price = 0,
            banner = "https://upload.wikimedia.org/wikipedia/commons/c/c8/Cappuccino_at_Sightglass_Coffee.jpg",
            rating = "4.8 (3432)",
            Captain = CaptainModel(
                Name = "Milk",
                Job = "Online Academy",
                Image = "https://celvine.sirv.com/esail/logo_bwa_new.webp"
            ),
            category = "Design",
            isPopular = false,
            isFlashSale = true,
            totalTime = "24m",
            totalVideo = 8
        ),
        CourseModel(
            id = 6,
            title = "Chai Latte",
            sortDesc = "Mempelajari penerapan slicing UI design dan integrasi data dengan API",
            desc = "Chai latte is a popular hot beverage that combines the flavors of spiced tea and steamed milk. It originated in India and has gained popularity worldwide.",
            isFree = false,
            price = 700000,
            banner = "https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2022/02/Chai-Latte-main-1.jpg",
            rating = "4.8 (120)",
            Captain = CaptainModel(
                Name = "Milk",
                Job = "Online Academy",
                Image = "https://celvine.sirv.com/esail/logo_bwa_new.webp"
            ),
            category = "Development",
            isPopular = false,
            isFlashSale = true,
            totalTime = "230h 10m",
            totalVideo = 15
        ),
        CourseModel(
            id = 7,
            title = "Crepes",
            sortDesc = "Use Blender to Create Beautiful 3D models for Video Games, 3D Printing & More. Beginners Level Course",
            desc = "Crepes are thin, delicate pancakes that originated in France. They are made from a simple batter consisting of flour, eggs, milk, and a small amount of butter or oil. The batter is typically mixed until smooth and then cooked on a flat griddle or frying pan.",
            isFree = false,
            price = 99000,
            banner = "https://www.frisianflag.com/storage/app/media/uploaded-files/crepes-pisang-coklat.jpg",
            rating = "4.8 (120)",
            Captain = CaptainModel(
                Name = "Milk",
                Job = "Helped More Than 1 Million Students",
                Image = "https://img-b.udemycdn.com/user/200_H/24317920_c8a2_2.jpg"
            ),
            category = "Design",
            isPopular = false,
            isFlashSale = true,
            totalTime = "13h",
            totalVideo = 101
        )
    )
}