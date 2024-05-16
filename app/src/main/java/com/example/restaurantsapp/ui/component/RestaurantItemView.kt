package com.example.restaurantsapp.ui.component

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.imageLoader
import com.example.restaurantsapp.R
import com.example.restaurantsapp.data.model.RestaurantImageItem
import com.example.restaurantsapp.utils.Constants.IMAGE_SIZE
import com.example.restaurantsapp.utils.Utils.imageRequest
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RestaurantItemView(
    images: List<RestaurantImageItem>,
    name: String,
    closedBucket: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { images.size })

    Column(modifier = Modifier.clickable { onClick.invoke() }) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 0.dp),
            pageSpacing = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Column {
                DisplayRestaurantImages(name, images, closedBucket, pagerState, page, context)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    HorizontalPagerIndicator(pageCount = images.size,
                        currentPage = pagerState.currentPage,
                        targetPage = pagerState.targetPage,
                        currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                val currentPage = pagerState.currentPage
                                val totalPages = images.size
                                val nextPage =
                                    if (currentPage < totalPages - 1) currentPage + 1 else 0
                                coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                            }

                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun DisplayRestaurantImages(
    name: String,
    images: List<RestaurantImageItem>,
    closedBucket: String,
    pagerState: PagerState,
    page: Int,
    context: Context
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .graphicsLayer {
            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
            alpha = lerp(
                start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        }) {
        FetchImageFromUrl(
            imageUrl = images[page].prefix.plus(IMAGE_SIZE).plus(images[page].suffix),
            context
        )
        Text(
            text = name,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(8.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        val resourceId = getResourceId(closedBucket)
        if (resourceId != -1) {
            Icon(
                painter = painterResource(id = resourceId),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(70.dp)
                    .padding(16.dp),
                tint = Color.Unspecified,
                contentDescription = "Restaurant Status"
            )
        }
    }
}

@Composable
fun FetchImageFromUrl(imageUrl: String, context: Context) {
    val imageRequest = imageRequest(context, imageUrl)
    AsyncImage(
        model = imageRequest,
        contentDescription = "Restaurant Photos",
        imageLoader = context.imageLoader,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
}


@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 10.dp,
    indicatorCornerRadius: Dp = 2.dp,
    indicatorPadding: Dp = 2.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentSize()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {
        repeat(pageCount) { page ->
            val (color, size) = if (currentPage == page || targetPage == page) {
                val pageOffset = ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)
                val size =
                    unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)
                indicatorColor.copy(
                    alpha = offsetPercentage
                ) to size
            } else {
                indicatorColor.copy(alpha = 0.25f) to unselectedIndicatorSize
            }
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(size)
                    .height(size / 2)
            )
        }
    }
}

fun getResourceId(closedBucket: String): Int = when (closedBucket) {
    "VeryLikelyOpen", "LikelyOpen" -> R.drawable.open
    "VeryLikelyClosed", "LikelyClosed" -> R.drawable.closed
    else -> -1
}