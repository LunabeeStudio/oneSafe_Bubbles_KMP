/*
 * Copyright (c) 2023-2024 Lunabee Studio
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

package studio.lunabee.bubbles.domain.model.contact

import kotlinx.datetime.Instant
import studio.lunabee.bubbles.domain.model.ConversationId
import studio.lunabee.bubbles.domain.model.SafeId
import studio.lunabee.bubbles.domain.model.contactkey.ContactSharedKey

data class Contact(
    val id: ContactId,
    val encName: ByteArray,
    val encSharedKey: ContactSharedKey?,
    val updatedAt: Instant,
    val encSharingMode: ByteArray,
    val sharedConversationId: ConversationId,
    val consultedAt: Instant?,
    val safeId: SafeId,
)
