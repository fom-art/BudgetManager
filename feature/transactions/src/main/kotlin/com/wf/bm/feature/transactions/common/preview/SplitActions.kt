package com.wf.bm.feature.transactions.common.preview

import com.wf.bm.core.model.User
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogActions

data class SplitActions(
    val updateUserSplit: (User, String) -> Unit,
    val removeSplit: () -> Unit,
    val setSplitEvenly: (Boolean) -> Unit,
    val friendsSelectionDialogActions: FriendsSelectionDialogActions,
)