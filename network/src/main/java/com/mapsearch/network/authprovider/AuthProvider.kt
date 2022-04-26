package com.mapsearch.network.authprovider

class AuthProvider() : IAuthProvider {
    override fun provideAuth(): String {
        return "y7X7cWCGj69fCvczQweU"
    }
}

interface IAuthProvider {
    fun provideAuth(): String
}