package com.synapse.social.studioasinc.feature.profile.profile.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import com.synapse.social.studioasinc.feature.shared.components.ButtonVariant
import com.synapse.social.studioasinc.feature.shared.components.ExpressiveButton
import com.synapse.social.studioasinc.feature.shared.components.animatedShape
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
    val coverHeight = 200.dp
    val overlap = 80.dp
    val contentPaddingTop = coverHeight - overlap
    val avatarSize = 100.dp
    val avatarPaddingTop = contentPaddingTop - (avatarSize * 0.30f)
    val textSpacerTop = (avatarSize * 0.75f) + Spacing.SmallMedium
    val avatarBorderWidth = 3.dp

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // صورة الغلاف مع تأثير شفاف
        CoverPhoto(
            coverImageUrl = coverImageUrl,
            scrollOffset = scrollOffset,
            isOwnProfile = isOwnProfile,
            onCoverClick = onCoverPhotoClick,
            height = coverHeight
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = contentPaddingTop)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(textSpacerTop))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // اسم المستخدم مع علامة التحقق
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
                            ModernVerifiedBadge()
                        }
                    }

                    // اليوزرنيم
                    Text(
                        text = "@$username",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    // النيك نيم
                    if (!nickname.isNullOrBlank()) {
                        Text(
                            text = nickname,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // إحصائيات على طريقة إنستا
                    ModernStatsRow(
                        postsCount = postsCount,
                        followersCount = followersCount,
                        followingCount = followingCount,
                        onStatsClick = onStatsClick
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // البايو
                if (!bio.isNullOrBlank()) {
                    ModernExpandableBio(
                        bio = bio,
                        expanded = bioExpanded,
                        onToggle = onToggleBio
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // أزرار الإجراءات الحديثة
                ModernActionButtons(
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

        // الأفاتار مع حلقة القصة
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = avatarPaddingTop)
        ) {
            ModernProfileImage(
                avatar = avatar,
                size = avatarSize,
                status = status,
                hasStory = hasStory,
                isOwnProfile = isOwnProfile,
                displayName = name ?: username,
                onClick = onProfileImageClick
            )
        }
    }
}

// علامة التحقق العصرية
@Composable
fun ModernVerifiedBadge(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(24.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                clip = false
            ),
        shape = CircleShape,
        color = Color(0xFF1DA1F2), // تويتر بلو
        contentColor = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(R.string.verified_account),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

// صورة الأفاتار العصرية
@Composable
private fun ModernProfileImage(
    avatar: String?,
    size: androidx.compose.ui.unit.Dp,
    status: UserStatus?,
    hasStory: Boolean,
    isOwnProfile: Boolean,
    displayName: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size + 8.dp)
            .shadow(
                elevation = if (hasStory) 8.dp else 0.dp,
                shape = CircleShape,
                clip = false,
                ambientColor = if (hasStory) Color(0xFF1DA1F2) else Color.Transparent,
                spotColor = if (hasStory) Color(0xFF1DA1F2) else Color.Transparent
            )
    ) {
        // حلقة القصة
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = if (hasStory) 4.dp else 0.dp,
                    color = if (hasStory) Color(0xFF1DA1F2) else Color.Transparent,
                    shape = CircleShape
                )
                .border(
                    width = if (hasStory) 2.dp else 0.dp,
                    color = if (hasStory) Color.White else Color.Transparent,
                    shape = CircleShape
                )
        ) {
            // صورة الأفاتار
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
                // ProfileAvatar(avatar, size, displayName)
                
                // دائرة مؤقتة
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                )
            }
        }
        
        // حالة النشاط
        if (status == UserStatus.ONLINE) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(14.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
                    .background(
                        color = Color(0xFF00C853),
                        shape = CircleShape
                    )
            )
        }
    }
}

// صف الإحصائيات العصري
@Composable
private fun ModernStatsRow(
    postsCount: Int,
    followersCount: Int,
    followingCount: Int,
    onStatsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ModernStatItem(
            count = followersCount,
            label = stringResource(R.string.followers),
            onClick = { onStatsClick("followers") }
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        ModernStatItem(
            count = followingCount,
            label = stringResource(R.string.following),
            onClick = { onStatsClick("following") }
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        ModernStatItem(
            count = postsCount,
            label = stringResource(R.string.posts),
            onClick = { onStatsClick("posts") }
        )
    }
}

@Composable
private fun ModernStatItem(
    count: Int,
    label: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        AnimatedCounter(count = count) { value ->
            Text(
                text = com.synapse.social.studioasinc.core.util.NumberFormatter.formatCount(value),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// البايو القابل للتوسيع العصري
@Composable
private fun ModernExpandableBio(
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (expanded) stringResource(R.string.show_less) else stringResource(R.string.see_more),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onToggle() }
            )
        }
    }
}

// أزرار الإجراءات العصرية
@Composable
private fun ModernActionButtons(
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
            // زر إضافة قصة
            Button(
                onClick = onAddStoryClick,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1DA1F2),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.add_story),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            // زر تعديل الملف الشخصي
            OutlinedButton(
                onClick = onEditProfileClick,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        } else {
            // زر متابعة متحرك
            ModernAnimatedFollowButton(
                isFollowing = isFollowing,
                isLoading = isFollowLoading,
                onClick = onFollowClick,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
            )

            // زر رسالة
            OutlinedButton(
                onClick = onMessageClick,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Message,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.m_message),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        
        // زر المزيد
        IconButton(
            onClick = onMoreClick,
            modifier = Modifier.size(44.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "More",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// زر المتابعة المتحرك العصري
@Composable
fun ModernAnimatedFollowButton(
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
            Color(0xFF1DA1F2)
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "followButtonColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isFollowing) {
            MaterialTheme.colorScheme.onSurface
        } else {
            Color.White
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "followButtonContentColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isFollowing) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            Color(0xFF1DA1F2)
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "followButtonBorderColor"
    )

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = modifier,
        enabled = !isLoading,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.7f),
            disabledContentColor = contentColor.copy(alpha = 0.7f)
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.ui.graphics.SolidColor(borderColor)
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = contentColor
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (following) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = if (following) stringResource(R.string.following) else stringResource(R.string.follow),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
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
            nickname = "أبو لهب",
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
