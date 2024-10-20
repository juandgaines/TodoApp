package com.juandgaines.todoapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juandgaines.todoapp.R
import com.juandgaines.todoapp.ui.theme.TodoAppTheme

@Composable
private fun HelloWorld(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello, World!",
        modifier = modifier
    )
}

@Composable
private fun StyledTextExample() {
    Text(
        text = "Hello, Jetpack Compose!",
        fontSize = 20.sp, // Font size in sp
        fontWeight = FontWeight.Bold, // Bold font weight
        color = Color.Blue, // Text color
        modifier = Modifier
            .padding(16.dp) // Padding around the text
            .background(Color.LightGray) // Background color behind the text
    )
}

@Composable
@Preview
private fun StyledTextTemplatePreview(){
    TodoAppTheme {
        StyledTextExample()
    }
}

@Composable
private fun ClickableTextExample() {

    var clickedVar = false
    var clickedState by remember { mutableStateOf(false) }

    Text(
        text = if (clickedState) "You clicked me!" else "Click me",
        fontSize = 18.sp,
        modifier = Modifier
            .clickable { clickedState = !clickedState } // Makes the text clickable
            .padding(16.dp)
            .background(Color.LightGray)
    )
}

@Composable
@Preview
private fun ClickableTextTemplatePreview(){
    TodoAppTheme {
        ClickableTextExample()
    }
}


@Composable
private fun IconExample() {
    Icon(
        imageVector = Icons.Default.Favorite, // Default material icon
        contentDescription = "Favorite Icon",
        tint = Color.Blue, // Change the color of the icon
        modifier = Modifier
            .size(48.dp) // Adjust the size of the icon
            .padding(8.dp) // Padding around the icon
    )
}

@Composable
@Preview
private fun IconTemplatePreview(){
    TodoAppTheme {
        IconExample()
    }
}

@Composable
private fun RoundedImageExample() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground), // Load from resources
        contentDescription = "Rounded Image",
        modifier = Modifier
            .size(100.dp) // Set size for the image
            //.clip(RoundedCornerShape(8.dp)) // Clip the image to have rounded corners
            .border(
                4.dp,
                Color.Gray,
                RoundedCornerShape(8.dp)
            ) // Add a border around the image
    )
}

@Composable
@Preview
private fun RoundedImageTemplatePreview(){
    TodoAppTheme {
        RoundedImageExample()
    }
}

@Composable
private fun IconButtonExample() {
    Button(
        onClick = { /* Handle click */ },
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Send, // Send icon from material design
            contentDescription = "Send",
            modifier = Modifier.size(24.dp) // Size of the icon inside the button
        )
        Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
        Text(text = "Send")
    }
}

@Composable
@Preview
private fun IconButtonTemplatePreview(){
    TodoAppTheme {
        IconButtonExample()
    }
}

@Composable
private fun OverflowTextExample(
    text: String
) {
    Text(
        text = text,
        maxLines = 1, // Limits the text to a single line
        overflow = TextOverflow.Ellipsis, // Displays '...' when the text is too long
        modifier = Modifier
            .padding(16.dp)
            .background(Color.LightGray)
            .fillMaxWidth() // Fills the entire width of the container
    )
}

@Preview
@Composable
private fun OverflowTextTemplatePreview(
    @PreviewParameter(TextProvider::class) text: String
){
    TodoAppTheme {
        OverflowTextExample(text)
    }
}

private class TextProvider:PreviewParameterProvider<String>{
    override val values: Sequence<String>
        get() = listOf(
            "Hello, Parameter Provider!",
            "This is a very long text that might not fit in one line."
        ).asSequence()
}