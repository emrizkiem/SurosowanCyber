# Surosowan Cyber Academy
This repository project learn and implement to Get Api The Movie DB

![Kotlin](https://img.shields.io/badge/Kotlin-Compatible-blue?style=flat-square)
![Android](https://img.shields.io/badge/Android-7.0-green?style=flat-square)
![Gradle](https://img.shields.io/badge/Gradle-7.3-green?style=flat-square)

![alt text](https://github.com/emrizkiem/SurosowanCyber/blob/master/design/Thumbnail.png)

## Getting Started
### Requirement

- Android 7.0+
- Android Studio Giraffe+
- Kotlin 1.9.10+
- Gradle 7.3+

### Installation
To use this repository you must do cloning via terminal or terminal on Android Studio
```kotlin
git clone https://github.com/emrizkiem/SurosowanCyber.git
```

after the repository is successfully cloned you must update the authorization in ApiConfig you have from [The Movie DB](https://developer.themoviedb.org/reference/intro/getting-started)
```kotlin
fun getApiService(): ApiService {
  val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
  val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor { chain ->
      val request = chain.request()
      val requestBuilder = request.newBuilder()
        .addHeader("accept", "application/json")
        .addHeader("Authorization", "XXX UPDATE IT HERE XXX")
        .build()
      chain.proceed(requestBuilder)
    }
    .build()
  val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
  return retrofit.create(ApiService::class.java)
}
```

## License

SurosowanCyber is available under the MIT license. See the LICENSE file for more info.
