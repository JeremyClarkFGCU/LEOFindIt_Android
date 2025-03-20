package com.example.leofindit.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


object LeoIcons {


    private var _Bluetooth: ImageVector? = null
    val Bluetooth: ImageVector
        get() {
            if (_Bluetooth != null) {
                return _Bluetooth!!
            }
            _Bluetooth = ImageVector.Builder(
                name = "Bluetooth Icon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(440f, 880f)
                    verticalLineToRelative(-304f)
                    lineTo(256f, 760f)
                    lineToRelative(-56f, -56f)
                    lineToRelative(224f, -224f)
                    lineToRelative(-224f, -224f)
                    lineToRelative(56f, -56f)
                    lineToRelative(184f, 184f)
                    verticalLineToRelative(-304f)
                    horizontalLineToRelative(40f)
                    lineToRelative(228f, 228f)
                    lineToRelative(-172f, 172f)
                    lineToRelative(172f, 172f)
                    lineTo(480f, 880f)
                    close()
                    moveToRelative(80f, -496f)
                    lineToRelative(76f, -76f)
                    lineToRelative(-76f, -74f)
                    close()
                    moveToRelative(0f, 342f)
                    lineToRelative(76f, -74f)
                    lineToRelative(-76f, -76f)
                    close()
                }
            }.build()
            return _Bluetooth!!
        }



    val SignalStrengthHigh: ImageVector
        get() {
            if (_SignalStrengthHigh != null) {
                return _SignalStrengthHigh!!
            }
            _SignalStrengthHigh = ImageVector.Builder(
                name = "Signal Strength Indicator: High",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(200f, 800f)
                    verticalLineToRelative(-240f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(240f)
                    close()
                    moveToRelative(240f, 0f)
                    verticalLineToRelative(-440f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(440f)
                    close()
                    moveToRelative(240f, 0f)
                    verticalLineToRelative(-640f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(640f)
                    close()
                }
            }.build()
            return _SignalStrengthHigh!!
        }

    private var _SignalStrengthHigh: ImageVector? = null


    val SignalStrengthMed: ImageVector
        get() {
            if (_SignalStrengthMed != null) {
                return _SignalStrengthMed!!
            }
            _SignalStrengthMed = ImageVector.Builder(
                name = "Signal Strength Indicator: Medium",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                    path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(200f, 800f)
                    verticalLineToRelative(-240f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(240f)
                    close()
                    moveToRelative(240f, 0f)
                    verticalLineToRelative(-440f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(440f)
                    close()
                }
            }.build()
            return _SignalStrengthMed!!
        }

    private var _SignalStrengthMed: ImageVector? = null


    val SignalStrengthLow: ImageVector
        get() {
            if (_SignalStrengthLow != null) {
                return _SignalStrengthLow!!
            }
            _SignalStrengthLow = ImageVector.Builder(
                name = "Signal Strength Indicator: Low",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(200f, 800f)
                    verticalLineToRelative(-240f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(240f)
                    close()
                }
            }.build()
            return _SignalStrengthLow!!
        }

    private var _SignalStrengthLow: ImageVector? = null

    }
