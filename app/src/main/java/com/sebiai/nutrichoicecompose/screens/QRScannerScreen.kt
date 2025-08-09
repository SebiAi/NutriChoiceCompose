package com.sebiai.nutrichoicecompose.screens

import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.SurfaceRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.screens.viewmodels.QRScannerScreenViewModel
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRScannerScreen(
    modifier: Modifier = Modifier,
    viewModel: QRScannerScreenViewModel = viewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    val possibleSurfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    if (cameraPermissionState.status.isGranted) {
        CameraPermissionGrantedContent(
            modifier = modifier,
            possibleSurfaceRequest = possibleSurfaceRequest
        )
    } else {
        CameraPermissionNotGrantedContent(
            modifier = modifier,
            showRationale = cameraPermissionState.status.shouldShowRationale,
            onPermissionRequest = cameraPermissionState::launchPermissionRequest
        )
    }
}

@Composable
private fun CameraPermissionGrantedContent(
    modifier: Modifier = Modifier,
    possibleSurfaceRequest: SurfaceRequest?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1F)
        ) {
            if (possibleSurfaceRequest != null) {
                CameraXViewfinder(
                    modifier = Modifier.fillMaxSize(),
                    surfaceRequest = possibleSurfaceRequest
                )
            } else {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {}
            }
            Icon(
                modifier = Modifier.size(200.dp).align(Alignment.Center),
                imageVector = Icons.Outlined.QrCodeScanner,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5F)
            )
        }
        Text(
            modifier = Modifier
                .padding(24.dp),
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.qr_scanner_hint),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraPermissionNotGrantedContent(
    modifier: Modifier = Modifier,

    showRationale: Boolean,
    onPermissionRequest: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        val text = if (showRationale) {
            stringResource(R.string.qr_scanner_camera_permission_description_with_rationale)
        } else {
            stringResource(R.string.qr_scanner_camera_permission_description)
        }
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Outlined.CameraAlt,
            contentDescription = null
        )
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onPermissionRequest
        ) {
            Text(
                text = stringResource(R.string.request_permission)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraPermissionNotGrantedContentWithoutRationalePreview() {
    NutriChoiceComposeTheme {
        CameraPermissionNotGrantedContent(
            modifier = Modifier.fillMaxSize(),
            showRationale = false,
            onPermissionRequest = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun CameraPermissionNotGrantedContentWithRationalePreview() {
    NutriChoiceComposeTheme {
        CameraPermissionNotGrantedContent(
            modifier = Modifier.fillMaxSize(),
            showRationale = true,
            onPermissionRequest = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun CameraPermissionGrantedContentPreview() {
    NutriChoiceComposeTheme {
        CameraPermissionGrantedContent(
            possibleSurfaceRequest = null
        )
    }
}