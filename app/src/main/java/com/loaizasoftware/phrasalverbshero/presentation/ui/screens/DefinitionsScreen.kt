package com.loaizasoftware.phrasalverbshero.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.loaizasoftware.core_ui.composables.AsyncImageWithLoader
import com.loaizasoftware.core_ui.composables.ContainerWithAnim
import com.loaizasoftware.core_ui.composables.DotsIndicator
import com.loaizasoftware.core_ui.general.AppBar
import com.loaizasoftware.core_ui.general.LoadingIndicator
import com.loaizasoftware.phrasalverbshero.R
import com.loaizasoftware.phrasalverbshero.domain.model.Example
import com.loaizasoftware.phrasalverbshero.domain.model.Definition
import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb
import com.loaizasoftware.phrasalverbshero.presentation.viewmodel.PhrasalVerbsViewModel
import com.loaizasoftware.phrasalverbshero.core.utils.FileUtils


@Composable
fun DefinitionsScreen(
    viewModel: PhrasalVerbsViewModel,
    phrasalVerb: PhrasalVerb,
    navHostController: NavHostController,
    getString: (Int) -> String
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            AppBar(
                title = "${phrasalVerb.phrasalVerb} Definitions",
                iconAppBar = Icons.AutoMirrored.Filled.ArrowBack
            ) {
                navHostController.navigateUp()
            }
        }
    ) { contentPadding ->

        LoadingIndicator(isVisible = viewModel.isLoadingDefinitions.value)

        if (!viewModel.isLoadingDefinitions.value) {
            DefinitionCards(
                contentPadding = contentPadding,
                definitionsList = viewModel.phrasalVerbDefinitions.value
            )
        }

    }

}

@Composable
fun DefinitionCards(
    contentPadding: PaddingValues,
    definitionsList: List<Definition>,
    isPreview: Boolean = false
) {

    ContainerWithAnim {

        val pagerState = rememberPagerState(pageCount = {
            definitionsList.size
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color.White)
                    .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth(),
                    //.background(color = Color.LightGray),
                    state = pagerState,
                ) { page ->
                    // Our page content

                    val definition = definitionsList[page]

                    Card(
                        Modifier
                            .padding(32.dp)
                            .height(maxHeight * 0.65f)
                            .width(maxWidth * 1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(Color.White.value)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp, bottom = 16.dp)
                                .background(Color.White),
                            text = "Definition ${page + 1}",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                .background(Color.White),
                            text = definition.definition,
                            style = TextStyle(
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 16.dp)
                                .background(Color.White),
                            text = "Example",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                .background(Color.White),
                            text = definition.examples?.getOrNull(0)?.exampleText
                                ?: "No example available",
                            style = TextStyle(
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        )

                        if (isPreview) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "Preview Image",
                                modifier = Modifier
                                    .height(250.dp)
                                    .width(250.dp)
                                    .padding(16.dp)
                                    .background(Color.White)
                                    .align(Alignment.CenterHorizontally)
                            )
                        } else {

                            PhrasalVerbImage(
                                imageName = definition.examples?.getOrNull(0)?.imageUrl
                                    ?: "default_image"
                            )

                        }


                    }

                }


            }

            if (definitionsList.size > 1) {

                DotsIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    totalDots = definitionsList.size,
                    selectedIndex = pagerState.currentPage,
                )

            }

        }

    }

}

@Composable
fun PhrasalVerbImage(imageName: String) {
    val imageUrl = produceState<String?>(initialValue = null, imageName) {
        value = FileUtils.getImageUrl(imageName)
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        AsyncImageWithLoader(imageUrl = imageUrl.value ?: "")

    }

}


@Preview
@Composable
fun PreviewDefinitionsScreen() {

    val definitionLists = listOf(
        Definition(
            1L,
            "Definition 1",
            listOf(
                Example(
                    1L, "Example 1",
                    imageUrl = ""
                )
            )
        ), Definition(2L, "Definition 2")
    )

    DefinitionCards(
        contentPadding = PaddingValues(0.dp),
        definitionsList = definitionLists,
        isPreview = true
    )

}