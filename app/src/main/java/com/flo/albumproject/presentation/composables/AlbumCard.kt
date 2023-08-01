package com.flo.albumproject.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.presentation.theme.ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumCard(modifier: Modifier, album: Album, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(album.color)),
        shape = RoundedCornerShape(5.dp),
        onClick = onClick
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (circleBlur, text) = createRefs()
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(circleBlur) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }.blur(radius = 90.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                color = ProjectTheme.colors.primaryVariant,
                shape = RoundedCornerShape(100)
            ) {}
            Text(
                modifier = Modifier.wrapContentSize().constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                text = album.id.toString(),
                fontSize = 45.sp,
                fontFamily = FontFamily.Cursive,
                color = Color.White
            )

        }
    }
}