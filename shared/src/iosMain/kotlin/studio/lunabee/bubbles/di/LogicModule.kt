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

import kotlinx.datetime.Clock
import org.koin.dsl.module
import studio.lunabee.bubbles.domain.repository.BubblesCryptoRepository
import studio.lunabee.bubbles.domain.repository.BubblesSafeRepository
import studio.lunabee.bubbles.repository.datasource.ContactKeyLocalDataSource
import studio.lunabee.bubbles.repository.datasource.ContactLocalDataSource
import studio.lunabee.messaging.domain.repository.MessagingCryptoRepository
import studio.lunabee.messaging.domain.repository.MessagingSettingsRepository
import studio.lunabee.messaging.repository.datasource.EnqueuedMessageLocalDataSource
import studio.lunabee.messaging.repository.datasource.HandShakeDataLocalDatasource
import studio.lunabee.messaging.repository.datasource.MessageLocalDataSource
import studio.lunabee.messaging.repository.datasource.SentMessageLocalDatasource

@Suppress("LongParameterList")
fun logicModule(
    enqueuedMessageLocalDataSource: EnqueuedMessageLocalDataSource,
    handShakeDataLocalDatasource: HandShakeDataLocalDatasource,
    messageLocalDataSource: MessageLocalDataSource,
    sentMessageLocalDatasource: SentMessageLocalDatasource,
    contactKeyLocalDataSource: ContactKeyLocalDataSource,
    contactLocalDataSource: ContactLocalDataSource,
    bubblesCryptoRepository: BubblesCryptoRepository,
    bubblesSafeRepository: BubblesSafeRepository,
    messagingCryptoRepository: MessagingCryptoRepository,
    messagingSettingsRepository: MessagingSettingsRepository,
) = listOf(
    bubblesRepositoryModule(
        bubblesCryptoRepository = bubblesCryptoRepository,
        bubblesSafeRepository = bubblesSafeRepository,
    ),
    bubblesDatasourceModule(
        contactKeyLocalDataSource = contactKeyLocalDataSource,
        contactLocalDataSource = contactLocalDataSource,
    ),
    messagingDatasourceModule(
        sentMessageLocalDatasource = sentMessageLocalDatasource,
        messageLocalDataSource = messageLocalDataSource,
        handShakeDataLocalDatasource = handShakeDataLocalDatasource,
        enqueuedMessageLocalDataSource = enqueuedMessageLocalDataSource,
    ),
    messagingRepositoryModule(
        messagingCryptoRepository = messagingCryptoRepository,
        messagingSettingsRepository = messagingSettingsRepository,
    ),
    bubblesUseCaseModule,
    messagingUseCaseModule,
    module { single<Clock> { Clock.System } },
)
