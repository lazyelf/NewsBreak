package com.example.newsbreak.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsbreak.R
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.ui.viewmodels.NewsItemViewModel

@Composable
fun NewsItemView(newsItem: NewsItem, viewModel: NewsItemViewModel = hiltViewModel()) {

    val uriHandler = LocalUriHandler.current

    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center,

        ) {
        Column(
            Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (newsItem.urlToImage != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(newsItem.urlToImage)
                        .crossfade(true)
                        .placeholder(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .build(),
                    contentDescription = "news image",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.FavoriteBorder,
                        contentDescription = "Save",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                NewsItemContent(
                    newsItem, viewModel,
                    modifier = Modifier.weight(1f)
                )

                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = "Visit",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                uriHandler.openUri(newsItem.url)
                            },
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun NewsItemContent(
    newsItem: NewsItem,
    viewModel: NewsItemViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Text(
                modifier = Modifier.weight(1f),
                text = newsItem.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = viewModel.parseTime(newsItem.publishedAt),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        if (newsItem.source.name !== null) {
            Text(
                text = newsItem.source.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (newsItem.description !== null) {
            Text(
                text = newsItem.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}