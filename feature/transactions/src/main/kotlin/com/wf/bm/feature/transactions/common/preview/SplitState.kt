package com.wf.bm.feature.transactions.common.preview

import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogState
import com.wf.bm.core.model.User

data class SplitState(
    val splitData: Map<User, String>?,
    val isSplitEvenly: Boolean,
    val friendsSelectionDialogState: FriendsSelectionDialogState
)