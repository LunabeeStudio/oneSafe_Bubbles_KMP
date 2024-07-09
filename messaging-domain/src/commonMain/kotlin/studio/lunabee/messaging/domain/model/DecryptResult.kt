/*
 * Copyright (c) 2023-2023 Lunabee Studio
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
 *
 * Created by Lunabee Studio / Date - 11/2/2023 - for the oneSafe6 SDK.
 * Last modified 02/11/2023 17:07
 */

package studio.lunabee.messaging.domain.model

import studio.lunabee.bubbles.domain.model.contact.ContactId
import studio.lunabee.bubbles.error.BubbleDoubleRatchetError
import studio.lunabee.bubbles.error.BubblesError

sealed interface DecryptResult {
    val contactId: ContactId
    val error: Error?

    data class NewMessage(
        override val contactId: ContactId,
    ) : DecryptResult {
        override val error: Error? = null
    }

    data class OwnMessage(
        override val contactId: ContactId,
    ) : DecryptResult {
        override val error: Error = Error.OWN_MESSAGE
    }

    data class AlreadyDecrypted(
        override val contactId: ContactId,
    ) : DecryptResult {
        override val error: Error = Error.ALREADY_DECRYPTED
    }

    companion object {
        fun fromDecryptIncomingMessageData(decryptIncomingMessageData: DecryptIncomingMessageData): DecryptResult {
            return when (decryptIncomingMessageData) {
                is DecryptIncomingMessageData.AlreadyDecryptedMessage -> AlreadyDecrypted(decryptIncomingMessageData.contactId)
                is DecryptIncomingMessageData.DecryptOwnMessage -> OwnMessage(decryptIncomingMessageData.contactId)
                is DecryptIncomingMessageData.NewMessage -> NewMessage(decryptIncomingMessageData.contactId)
            }
        }
    }

    enum class Error(val error: BubblesError) {
        ALREADY_DECRYPTED(BubbleDoubleRatchetError(BubbleDoubleRatchetError.Code.MessageKeyNotFound)),
        OWN_MESSAGE(BubbleDoubleRatchetError(BubbleDoubleRatchetError.Code.MessageKeyNotFound)),
    }
}
