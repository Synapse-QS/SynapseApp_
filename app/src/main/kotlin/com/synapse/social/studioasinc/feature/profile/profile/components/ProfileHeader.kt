package com.synapse.social.studioasinc.feature.profile.profile.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
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
    val coverHeight = Sizes.HeightExtraLarge
    val overlap = Sizes.HeightMedium
    val contentPaddingTop = coverHeight - overlap
    val avatarSize = Sizes.AvatarHuge
    val avatarPaddingTop = contentPaddingTop - (avatarSize * 0.20f)
    val textSpacerTop = (avatarSize * 0.80f) + Spacing.SmallMedium
    val avatarBorderWidth = Spacing.ExtraSmall

    // Entrance animation
    val enterTransition = remember { 
        MutableTransitionState(false).apply { targetState = true }
    }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        AnimatedContent(
            targetState = Unit,
            transitionSpec = {
                fadeIn(animationSpec = tween(500, delayMillis = 100)) + 
                scaleIn(initialScale = 0.95f, animationSpec = tween(500))
            },
            label = "profileEntrance"
        ) {
            CoverPhoto(
                coverImageUrl = coverImageUrl,
                scrollOffset = scrollOffset,
                isOwnProfile = isOwnProfile,
                onCoverClick = onCoverPhotoClick,
                height = coverHeight
            )
        }

        AnimatedContent(
            targetState = Unit,
            transitionSpec = {
                fadeIn(animationSpec = tween(500, delayMillis = 200)) + 
                slideInVertically(initialOffsetY = { it / 2 }) +
                scaleIn(initialScale = 0.97f, animationSpec = tween(500))
            },
            label = "contentEntrance"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = contentPaddingTop)
                    .clip(RoundedCornerShape(topStart = Sizes.CornerMassive, topEnd = Sizes.CornerMassive))
                    .background(MaterialTheme.colorScheme.surface)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(topStart = Sizes.CornerMassive, topEnd = Sizes.CornerMassive),
                        clip = false
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.Medium)
                ) {
                    Spacer(modifier = Modifier.height(textSpacerTop))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall)
                        ) {
                            Text(
                                text = name ?: username,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )

                            if (isVerified) {
                                AnimatedVerifiedBadge()
                            }
                        }

                        Text(
                            text = stringResource(R.string.common_at_username, username),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (!nickname.isNullOrBlank()) {
                            Text(
                                text = nickname,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(Spacing.Medium))

                        InlineStatsText(
                            postsCount = postsCount,
                            followersCount = followersCount,
                            followingCount = followingCount,
                            onStatsClick = onStatsClick
                        )
                    }

                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    if (!bio.isNullOrBlank()) {
                        ExpandableBio(
                            bio = bio,
                            expanded = bioExpanded,
                            onToggle = onToggleBio
                        )
                        Spacer(modifier = Modifier.height(Spacing.Medium))
                    }

                    ProfileActionButtons(
                        isOwnProfile = isOwnProfile,
                        isFollowing = isFollowing,
                        isFollowLoading = isFollowLoading,
                        onEditProfileClick = onEditProfileClick,
                        onAddStoryClick = onAddStoryClick,
                        onFollowClick = onFollowClick,
                        onMessageClick = onMessageClick,
                        onMoreClick = onMoreClick
                    )

                    Spacer(modifier = Modifier.height(Spacing.Medium))
                }
            }
        }

        // Avatar with animation
        AnimatedContent(
            targetState = Unit,
            transitionSpec = {
                fadeIn(animationSpec = tween(500, delayMillis = 300)) + 
                scaleIn(initialScale = 0.8f, animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                ))
            },
            label = "avatarEntrance"
        ) {
            Box(
                modifier = Modifier
                    .padding(start = Spacing.Medium)
                    .padding(top = avatarPaddingTop)
            ) {
                ProfileImageWithRing(
                    avatar = avatar,
                    size = avatarSize,
                    status = status,
                    hasStory = hasStory,
                    isOwnProfile = isOwnProfile,
                    displayName = name ?: username,
                    onClick = onProfileImageClick,
                    modifier = Modifier.border(avatarBorderWidth, MaterialTheme.colorScheme.surface, CircleShape)
                )
            }
        }
    }
}

@Composable
fun AnimatedVerifiedBadge(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "verifiedBadge")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "verifiedScale"
    )

    val glow by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "verifiedGlow"
    )

    Surface(
        modifier = modifier
            .size(Sizes.IconLarge)
            .scale(scale)
            .shadow(
                elevation = glow.dp,
                shape = SevenSidedCookieShape(),
                clip = false,
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
        shape = SevenSidedCookieShape(),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = stringResource(R.string.verified_account),
                modifier = Modifier
                    .size(Sizes.IconSemiMedium)
                    .graphicsLayer {
                        rotationZ = if (scale > 1.05f) 15f else 0f
                    }
            )
        }
    }
}

@Composable
private fun ExpandableBio(
    bio: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shouldCollapse = bio.length > 150

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
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (isExpanded || !shouldCollapse) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable(enabled = shouldCollapse) { onToggle() }
                    .animateContentSize()
            )
        }

        if (shouldCollapse) {
            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
            AnimatedContent(
                targetState = expanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(200)) + 
                    slideInHorizontally(initialOffsetX = { if (expanded) -20 else 20 })
                },
                label = "bioToggle"
            ) { isExpanded ->
                Text(
                    text = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.see_more),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { onToggle() }
                        .graphicsLayer {
                            scaleX = if (isExpanded) 1.05f else 1f
                            scaleY = if (isExpanded) 1.05f else 1f
                        }
                )
            }
        }
    }
}

@Composable
private fun ProfileActionButtons(
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
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isOwnProfile) {
            AnimatedExpressiveButton(
                onClick = onAddStoryClick,
                text = stringResource(R.string.add_story),
                modifier = Modifier
                    .weight(1f)
                    .height(Spacing.ButtonHeight),
                variant = ButtonVariant.Filled
            )

            AnimatedExpressiveButton(
                onClick = onEditProfileClick,
                text = stringResource(R.string.edit_profile),
                modifier = Modifier
                    .weight(1f)
                    .height(Spacing.ButtonHeight),
                variant = ButtonVariant.FilledTonal
            )
        } else {
            ModernAnimatedFollowButton(
                isFollowing = isFollowing,
                isLoading = isFollowLoading,
                onClick = onFollowClick,
                modifier = Modifier
                    .weight(1f)
                    .height(Spacing.ButtonHeight)
            )

            AnimatedExpressiveButton(
                onClick = onMessageClick,
                text = stringResource(R.string.m_message),
                modifier = Modifier
                    .weight(1f)
                    .height(Spacing.ButtonHeight),
                variant = ButtonVariant.Outlined
            )
        }
    }
}

@Composable
fun AnimatedExpressiveButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Filled
) {
    var isPressed by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )

    ExpressiveButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        text = text,
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        variant = variant
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ModernAnimatedFollowButton(
    isFollowing: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "followButtonScale"
    )

    val containerColor by animateColorAsState(
        targetValue = if (isFollowing) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            MaterialTheme.colorScheme.primary
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "followButtonColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isFollowing) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            MaterialTheme.colorScheme.onPrimary
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "followButtonContentColor"
    )

    val shadowElevation by animateFloatAsState(
        targetValue = if (isPressed) 0f else 4f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "followButtonShadow"
    )

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = shadowElevation.dp,
                shape = ButtonDefaults.animatedShape(),
                clip = false
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        enabled = !isLoading,
        shape = ButtonDefaults.animatedShape(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.7f),
            disabledContentColor = contentColor.copy(alpha = 0.7f)
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Sizes.IconMedium),
                    strokeWidth = Sizes.BorderDefault,
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
                            AnimatedContent(
                                targetState = true,
                                transitionSpec = {
                                    scaleIn(initialScale = 0f, animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )) + fadeIn()
                                },
                                label = "checkIcon"
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = contentColor
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                        Text(
                            text = if (following) stringResource(R.string.following) else stringResource(R.string.follow),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InlineStatsText(
    postsCount: Int,
    followersCount: Int,
    followingCount: Int,
    onStatsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedPosts = com.synapse.social.studioasinc.core.util.NumberFormatter.formatCount(postsCount)

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedStatRow(
            count = followersCount,
            label = stringResource(R.string.followers).lowercase(),
            onClick = { onStatsClick("followers") }
        )

        Text(
            text = stringResource(R.string.common_bullet_separator),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.animateContentSize()
        )

        AnimatedStatRow(
            count = followingCount,
            label = stringResource(R.string.following).lowercase(),
            onClick = { onStatsClick("following") }
        )

        Text(
            text = stringResource(R.string.common_bullet_separator),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.animateContentSize()
        )

        AnimatedStatRow(
            count = postsCount,
            label = stringResource(R.string.posts).lowercase(),
            onClick = { onStatsClick("posts") },
            isPost = true
        )
    }
}

@Composable
private fun AnimatedStatRow(
    count: Int,
    label: String,
    onClick: () -> Unit,
    isPost: Boolean = false
) {
    var isPressed by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "statRowScale"
    )

    Row(
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            }
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isPost) {
            Text(
                text = com.synapse.social.studioasinc.core.util.NumberFormatter.formatCount(count),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            AnimatedCounter(count = count) { value ->
                Text(
                    text = com.synapse.social.studioasinc.core.util.NumberFormatter.formatCount(value),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.width(Spacing.ExtraSmall))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
            name = "John Doe",
            username = "johndoe",
            nickname = "JD",
            bio = "Software developer | Tech enthusiast | Coffee lover ☕️ | Building amazing things with code every day.",
            isVerified = true,
            hasStory = true,
            postsCount = 142,
            followersCount = 12345,
            followingCount = 567,
            isOwnProfile = true,
            onProfileImageClick = {},
            onEditProfileClick = {},
            onAddStoryClick = {},
            onMoreClick = {},
            onStatsClick = {}
        )
    }
}
