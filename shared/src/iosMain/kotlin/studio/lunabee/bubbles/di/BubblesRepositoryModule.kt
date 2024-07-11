/*
 * Copyright (c) 2024 Lunabee Studio
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

package studio.lunabee.bubbles.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import studio.lunabee.bubbles.domain.repository.BubblesCryptoRepository
import studio.lunabee.bubbles.domain.repository.BubblesSafeRepository
import studio.lunabee.bubbles.domain.repository.ContactKeyRepository
import studio.lunabee.bubbles.domain.repository.ContactRepository
import studio.lunabee.bubbles.repository.ContactKeyRepositoryImpl
import studio.lunabee.bubbles.repository.ContactRepositoryImpl

fun bubblesRepositoryModule(
    bubblesCryptoRepository: BubblesCryptoRepository,
    bubblesSafeRepository: BubblesSafeRepository,
) = module {
    single<ContactKeyRepository> { ContactKeyRepositoryImpl(get()) }
    single<BubblesCryptoRepository> { bubblesCryptoRepository }
    single<ContactRepository> { ContactRepositoryImpl(get()) }
    single<BubblesSafeRepository> { bubblesSafeRepository }
}

class BubblesRepositories : KoinComponent {
    val contactKeyRepository: ContactKeyRepository by inject()
    val bubblesCryptoRepository: BubblesCryptoRepository by inject()
    val contactRepository: ContactRepository by inject()
    val bubblesSafeRepository: BubblesSafeRepository by inject()
}
