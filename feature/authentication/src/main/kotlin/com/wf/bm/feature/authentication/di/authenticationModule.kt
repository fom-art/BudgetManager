package com.wf.bm.feature.authentication.di


import com.wf.bm.feature.authentication.forgot_password.ForgotPasswordViewModel
import com.wf.bm.feature.authentication.registration.RegistrationViewModel
import com.wf.bm.feature.authentication.sign_in.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authenticationModule = module {
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::SignInViewModel)
}