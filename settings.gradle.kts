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

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.60.2"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions")
}

rootProject.name = "oneSafe Bubbles KMP"
include(":oneSafe_Bubbles_KMP:bubbles-domain")
project(":oneSafe_Bubbles_KMP:bubbles-domain").projectDir = File("bubbles-domain")

include(":oneSafe_Bubbles_KMP:bubbles-repository")
project(":oneSafe_Bubbles_KMP:bubbles-repository").projectDir = File("bubbles-repository")

include(":oneSafe_Bubbles_KMP:messaging-domain")
project(":oneSafe_Bubbles_KMP:messaging-domain").projectDir = File("messaging-domain")

include(":oneSafe_Bubbles_KMP:messaging-repository")
project(":oneSafe_Bubbles_KMP:messaging-repository").projectDir = File("messaging-repository")

include(":oneSafe_Bubbles_KMP:shared")
project(":oneSafe_Bubbles_KMP:shared").projectDir = File("shared")

include(":oneSafe_Bubbles_KMP:error")
project(":oneSafe_Bubbles_KMP:error").projectDir = File("error")
