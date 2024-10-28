package com.example.subfundamental.data.local

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExcutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()

}