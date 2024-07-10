/*
 * Copyright (c) 2023 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("java-library")
    `lunabee-publish`
}

group = "studio.lunabee.bubbles.di"
description = "DI entry point for ios app"
version = "0.0.1-SNAPSHOT"

kotlin {
    jvm()
    listOf(
        iosSimulatorArm64(),
        iosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "bubbles"
            export(project(":bubbles-domain"))
            export(project(":bubbles-repository"))
            export(project(":messaging-domain"))
            export(project(":messaging-repository"))
            export(project(":error"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":bubbles-domain"))
            api(project(":bubbles-repository"))
            api(project(":messaging-domain"))
            api(project(":messaging-repository"))
            api(project(":error"))
        }

        iosMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
