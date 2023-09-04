package com.example.onlineaudioplayer.di

import com.example.onlineaudioplayer.feature.domian.usecase.channels.AddChannelUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.channels.DeleteChannelUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.channels.GetChannelsUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.channels.UpdateChannelUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.exoplayer.CreateRadioChannelMediaItemUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.locale.ChangeLocaleUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.locale.GetCurrentLocaleUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.validator.ChannelNameValidatorUseCase
import com.example.onlineaudioplayer.feature.domian.usecase.validator.ChannelUrlValidatorUseCase
import org.koin.dsl.module

val useCasesModule = module {
    //channel
    single { GetChannelsUseCase(get()) }
    single { AddChannelUseCase(get()) }
    single { UpdateChannelUseCase(get()) }
    single { DeleteChannelUseCase(get()) }

    //locale
    single { GetCurrentLocaleUseCase() }
    single { ChangeLocaleUseCase() }

    //validator
    single { ChannelUrlValidatorUseCase() }
    single { ChannelNameValidatorUseCase() }

    //exoplayer
    single { CreateRadioChannelMediaItemUseCase() }
}