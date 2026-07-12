package com.synapse.social.studioasinc.feature.profile.profile.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import com.synapse.social.studioasinc.R
import androidx.compose.ui.unit.dp
import com.synapse.social.studioasinc.feature.shared.components.AnimatedCounter
import com.synapse.social.studioasinc.feature.shared.theme.Spacing
import com.synapse.social.studioasinc.feature.shared.theme.Sizes
import com.synapse.social.studioasinc.domain.model.UserStatus

@Composable
fun ProfileHeader(
    avatar: String?,
    status: UserStatus?,
    coverImageUrl: String?,
    name: String?,
    username: String,
    nickname: String?,
    bio: String?,
    isVerified: Boolean,
    hasStory: Boolean,
    postsCount: Int,
    followersCount: Int,
    followingCount: Int,
    isOwnProfile: Boolean,
    onProfileImageClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onAddStoryClick: () -> Unit,
    onMoreClick: () -> Unit,
    onStatsClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isFollowing: Boolean = false,
    isFollowLoading: Boolean = false,
    onFollowClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onCoverPhotoClick: () -> Unit = {},
    scrollOffset: Float = 0f,
    bioExpanded: Boolean = false,
    onToggleBio: () -> Unit = {}
) {
    val coverHeight = 180.dp
    val overlap = 70.dp
    val contentPaddingTop = coverHeight - overlap
    val avatarSize = 88.dp
    val avatarPaddingTop = contentPaddingTop - (avatarSize * 0.20f)
    val textSpacerTop = (avatarSize * 0.70f) + 8.dp

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // صورة الغلاف
        CoverPhoto(
            coverImageUrl = coverImageUrl,
            scrollOffset = scrollOffset,
            isOwnProfile = isOwnProfile,
            onCoverClick = onCoverPhotoClick,
            height = coverHeight
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = contentPaddingTop)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // الأفاتار
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                InstagramProfileImage(
                    avatar = avatar,
                    size = avatarSize,
                    status = status,
                    hasStory = hasStory,
                    isOwnProfile = isOwnProfile,
                    displayName = name ?: username,
                    onClick = onProfileImageClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = avatarPaddingTop)
                )
            }

            Spacer(modifier = Modifier.height(textSpacerTop))

            // المحتوى
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // الاسم وعلامة التحقق
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = name ?: username,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    if (isVerified) {
                        InstagramVerifiedBadge()
                    }
                }

                // البايو
                if (!bio.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    InstagramBio(
                        bio = bio,
                        expanded = bioExpanded,
                        onToggle = onToggleBio
                    )
                }

                // النيك نيم
                if (!nickname.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = nickname,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // إحصائيات إنستا
                InstagramStatsRow(
                    postsCount = postsCount,
                    followersCount = followersCount,
                    followingCount = followingCount,
                    onStatsClick = onStatsClick
                )

                Spacer(modifier = Modifier.height(12.dp))

                // أزرار إنستا
                InstagramActionButtons(
                    isOwnProfile = isOwnProfile,
                    isFollowing = isFollowing,
                    isFollowLoading = isFollowLoading,
                    onEditProfileClick = onEditProfileClick,
                    onAddStoryClick = onAddStoryClick,
                    onFollowClick = onFollowClick,
                    onMessageClick = onMessageClick,
                    onMoreClick = onMoreClick
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// علامة التحقق بتصميم إنستا بالضبط
@Composable
fun InstagramVerifiedBadge(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .background(
                color = Color(0xFF0095F6),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(R.string.verified_account),
            modifier = Modifier
                .fillMaxSize(0.6f)
                .align(Alignment.Center),
            tint = Color.White
        )
    }
}

// صورة الأفاتار بتصميم إنستا بالضبط
@Composable
private fun InstagramProfileImage(
    avatar: String?,
    size: androidx.compose.ui.unit.Dp,
    status: UserStatus?,
    hasStory: Boolean,
    isOwnProfile: Boolean,
    displayName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val storyColors = listOf(
        Color(0xFF833AB4),
        Color(0xFFE1306C),
        Color(0xFFF77737),
        Color(0xFFFCAF45)
    )

    Box(
        modifier = modifier
            .size(size)
    ) {
        // حلقة القصة
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (hasStory) {
                        Modifier.shadow(
                            elevation = 0.dp,
                            shape = CircleShape,
                            clip = false,
                            ambientColor = Color.Transparent,
                            spotColor = Color.Transparent
                        )
                    } else Modifier
                )
        ) {
            // التدرج اللوني لحلقة القصة
            if (hasStory) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .background(
                            brush = Brush.sweepGradient(
                                colors = storyColors,
                                center = Offset(0.5f, 0.5f)
                            ),
                            shape = CircleShape
                        )
                )
            }

            // الأفاتار
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (hasStory) 6.dp else 0.dp)
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    .clickable { onClick() }
            ) {
                // هنا مكان صورة الأفاتار الفعلية
                // ProfileAvatar(avatar, size - (if (hasStory) 12.dp else 0.dp), displayName)
                
                // دائرة مؤقتة للعرض
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

// البايو بتصميم إنستا
@Composable
private fun InstagramBio(
    bio: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shouldCollapse = bio.length > 120

    Column(modifier = modifier) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                (fadeIn(animationSpec = tween(200)) + expandVertically())
                    .togetherWith(fadeOut(animationSpec = tween(200)) + shrinkVertically())
            },
            label = "bioExpand"
        ) { isExpanded ->
            Text(
                text = bio,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (isExpanded || !shouldCollapse) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable(enabled = shouldCollapse) { onToggle() },
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (shouldCollapse) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (expanded) "أقل" else "المزيد",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF737373)
                ),
                modifier = Modifier.clickable { onToggle() }
            )
        }
    }
}

// إحصائيات إنستا
@Composable
private fun InstagramStatsRow(
    postsCount: Int,
    followersCount: Int,
    followingCount: Int,
    onStatsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InstagramStatItem(
            count = postsCount,
            label = stringResource(R.string.posts),
            onClick = { onStatsClick("posts") }
        )
        
        InstagramStatItem(
            count = followersCount,
            label = stringResource(R.string.followers),
            onClick = { onStatsClick("followers") }
        )
        
        InstagramStatItem(
            count = followingCount,
            label = stringResource(R.string.following),
            onClick = { onStatsClick("following") }
        )
    }
}

@Composable
private fun InstagramStatItem(
    count: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
    ) {
        AnimatedCounter(count = count) { value ->
            Text(
                text = com.synapse.social.studioasinc.core.util.NumberFormatter.formatCount(value),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF737373)
        )
    }
}

// أزرار إنستا
@Composable
private fun InstagramActionButtons(
    isOwnProfile: Boolean,
    isFollowing: Boolean,
    isFollowLoading: Boolean,
    onEditProfileClick: () -> Unit,
    onAddStoryClick: () -> Unit,
    onFollowClick: () -> Unit,
    onMessageClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isOwnProfile) {
            // زر تعديل الملف الشخصي - زي إنستا بالضبط
            OutlinedButton(
                onClick = onEditProfileClick,
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFDBDBDB))
                )
            ) {
                Text(
                    text = "تعديل الملف الشخصي",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    fontSize = MaterialTheme.typography.labelLarge.fontSize
                )
            }

            // زر مشاركة
            OutlinedButton(
                onClick = { /* مشاركة */ },
                modifier = Modifier
                    .weight(0.3f)
                    .height(36.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFDBDBDB))
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonAdd,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        } else {
            // زر متابعة - زي إنستا بالضبط
            InstagramFollowButton(
                isFollowing = isFollowing,
                isLoading = isFollowLoading,
                onClick = onFollowClick,
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
            )

            // زر رسالة
            OutlinedButton(
                onClick = onMessageClick,
                modifier = Modifier
                    .weight(0.4f)
                    .height(36.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFDBDBDB))
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // زر المزيد
        IconButton(
            onClick = onMoreClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// زر المتابعة بتصميم إنستا
@Composable
fun InstagramFollowButton(
    isFollowing: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    val containerColor by animateColorAsState(
        targetValue = if (isFollowing) {
            Color.Transparent
        } else {
            Color(0xFF0095F6)
        },
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "followButtonColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isFollowing) {
            MaterialTheme.colorScheme.onSurface
        } else {
            Color.White
        },
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "followButtonContentColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isFollowing) {
            Color(0xFFDBDBDB)
        } else {
            Color(0xFF0095F6)
        },
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "followButtonBorderColor"
    )

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = modifier,
        enabled = !isLoading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.7f),
            disabledContentColor = contentColor.copy(alpha = 0.7f)
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(borderColor)
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = if (isFollowing) MaterialTheme.colorScheme.onSurface else Color.White
                )
            } else {
                AnimatedContent(
                    targetState = isFollowing,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(150)) + scaleIn(initialScale = 0.8f))
                            .togetherWith(fadeOut(animationSpec = tween(150)) + scaleOut(targetScale = 0.8f))
                    },
                    label = "followButtonContent"
                ) { following ->
                    Text(
                        text = if (following) "متابَع" else "متابعة",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = if (following) FontWeight.Medium else FontWeight.SemiBold
                        ),
                        fontSize = MaterialTheme.typography.labelLarge.fontSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileHeaderPreview() {
    MaterialTheme {
        ProfileHeader(
            avatar = null,
            status = UserStatus.ONLINE,
            coverImageUrl = null,
            name = "أبوبكر",
            username = "abo_bakr",
            nickname = "@abo_bakr",
            bio = "ولاكسوها الذهب تكشكش زي الركشه وتسجلني في تلفونها ❤️",
            isVerified = true,
            hasStory = true,
            postsCount = 0,
            followersCount = 0,
            followingCount = 0,
            isOwnProfile = true,
            onProfileImageClick = {},
            onEditProfileClick = {},
            onAddStoryClick = {},
            onMoreClick = {},
            onStatsClick = {}
        )
    }
}
