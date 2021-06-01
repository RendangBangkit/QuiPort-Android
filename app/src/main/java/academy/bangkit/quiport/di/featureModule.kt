package academy.bangkit.quiport.di

import academy.bangkit.quiport.features.ExtraFeatures
import org.koin.dsl.module

val featureModule = module {
    single { ExtraFeatures(get()) }
}