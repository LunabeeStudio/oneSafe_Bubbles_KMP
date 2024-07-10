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

import org.koin.core.context.startKoin
import studio.lunabee.bubbles.domain.repository.BubblesCryptoRepository
import studio.lunabee.bubbles.domain.repository.SafeRepository
import studio.lunabee.bubbles.repository.datasource.ContactKeyLocalDataSource
import studio.lunabee.bubbles.repository.datasource.ContactLocalDataSource
import studio.lunabee.messaging.repository.datasource.EnqueuedMessageLocalDataSource
import studio.lunabee.messaging.repository.datasource.HandShakeDataLocalDatasource
import studio.lunabee.messaging.repository.datasource.MessageLocalDataSource
import studio.lunabee.messaging.repository.datasource.SentMessageLocalDatasource

@Suppress("LongParameterList")
fun initKoin(
    enqueuedMessageLocalDataSource: EnqueuedMessageLocalDataSource,
    handShakeDataLocalDatasource: HandShakeDataLocalDatasource,
    messageLocalDataSource: MessageLocalDataSource,
    sentMessageLocalDatasource: SentMessageLocalDatasource,
    contactKeyLocalDataSource: ContactKeyLocalDataSource,
    contactLocalDataSource: ContactLocalDataSource,
    bubblesCryptoRepository: BubblesCryptoRepository,
    safeRepository: SafeRepository,
) {
    startKoin {
        modules(
            logicModule(
                enqueuedMessageLocalDataSource = enqueuedMessageLocalDataSource,
                handShakeDataLocalDatasource = handShakeDataLocalDatasource,
                messageLocalDataSource = messageLocalDataSource,
                sentMessageLocalDatasource = sentMessageLocalDatasource,
                contactKeyLocalDataSource = contactKeyLocalDataSource,
                contactLocalDataSource = contactLocalDataSource,
                bubblesCryptoRepository = bubblesCryptoRepository,
                safeRepository = safeRepository,
            ),
        )
    }
}
