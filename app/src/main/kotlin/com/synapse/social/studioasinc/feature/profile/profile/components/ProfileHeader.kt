package com.synapse.social.studioasinc.feature.profile.profile.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
        // Cover Photo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight)
                .clickable(enabled = isOwnProfile) { onCoverPhotoClick() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFFE8E8E8)
                    )
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f)
                            )
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = contentPaddingTop)
                .background(MaterialTheme.colorScheme.surface)
        ) {
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
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

                if (!bio.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    InstagramBio(
                        bio = bio,
                        expanded = bioExpanded,
                        onToggle = onToggleBio
                    )
                }

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

                InstagramStatsRow(
                    postsCount = postsCount,
                    followersCount = followersCount,
                    followingCount = followingCount,
                    onStatsClick = onStatsClick
                )

                Spacer(modifier = Modifier.height(12.dp))

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
    
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "profileScale"
    )

    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (hasStory) 6.dp else 0.dp)
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
            ) {
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
                text = if (expanded) "less" else "more",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF737373)
                ),
                modifier = Modifier.clickable { onToggle() }
            )
        }
    }
}

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
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "statScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            }
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
            AnimatedButton(
                onClick = onEditProfileClick,
                modifier = Modifier.weight(1f),
                text = "Edit Profile"
            )

            AnimatedIconButton(
                onClick = { /* Share */ },
                modifier = Modifier.weight(0.3f),
                icon = Icons.Outlined.PersonAdd
            )
        } else {
            InstagramFollowButton(
                isFollowing = isFollowing,
                isLoading = isFollowLoading,
                onClick = onFollowClick,
                modifier = Modifier.weight(1f)
            )

            AnimatedButton(
                onClick = onMessageClick,
                modifier = Modifier.weight(0.4f),
                text = "Message",
                isOutlined = true
            )
        }

        AnimatedIconButton(
            onClick = onMoreClick,
            modifier = Modifier.size(36.dp),
            icon = Icons.Default.MoreHoriz,
            size = 20
        )
    }
}

@Composable
fun AnimatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    isOutlined: Boolean = false
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )
    
    val haptic = LocalHapticFeedback.current

    if (isOutlined) {
        OutlinedButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
            modifier = modifier
                .height(36.dp)
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
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.dp,
                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFDBDBDB))
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    } else {
        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
            modifier = modifier
                .height(36.dp)
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
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0095F6),
                contentColor = Color.White
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun AnimatedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    size: Int = 18
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "iconButtonScale"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) 45f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "iconButtonRotation"
    )
    
    val haptic = LocalHapticFeedback.current

    IconButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = modifier
            .scale(scale)
            .graphicsLayer {
                rotationZ = rotation
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(size.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun InstagramFollowButton(
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
            Color.Transparent
        } else {
            Color(0xFF0095F6)
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
            Color(0xFFDBDBDB)
        } else {
            Color(0xFF0095F6)
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "followButtonBorderColor"
    )

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = modifier
            .height(36.dp)
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
                        (fadeIn(animationSpec = tween(200)) + scaleIn(initialScale = 0.8f))
                            .togetherWith(fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.8f))
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
                                modifier = Modifier.size(16.dp),
                                tint = contentColor
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = if (following) "Following" else "Follow",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (following) FontWeight.Medium else FontWeight.SemiBold
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
            name = "Abubakr",
            username = "abo_bakr",
            nickname = "@abo_bakr",
            bio = "ولاكسوها الذهب تكشكش زي الركشه وتسجلني في تلفونها ❤️",
            isVerified = true,
            hasStory = true,
            postsCount = 0,
            followersCount = 0,
            followingCount = 0,
            isOwnProfile = false,
            onProfileImageClick = {},
            onEditProfileClick = {},
            onAddStoryClick = {},
            onMoreClick = {},
            onStatsClick = {}
        )
    }
}
