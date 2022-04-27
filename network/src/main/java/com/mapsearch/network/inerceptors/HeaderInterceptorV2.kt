package com.mapsearch.network.inerceptors

import com.mapsearch.network.authprovider.IAuthProvider
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptorV2(var authProvider: IAuthProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .header("Accept", "application/com.donkeyrepublic.v2")
                .header("Content-Type", "application/json")
                .header("Authorization", authProvider.provideAuth())
                .build()
        )
    }

}