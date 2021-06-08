package academy.bangkit.quiport.core.di

import academy.bangkit.quiport.core.features.ExtraFeatures
import org.koin.dsl.module

val featureModule = module {
    single { ExtraFeatures(get()) }
}