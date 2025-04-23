# LEOFindIt - Android Application

## Overview

LEOFindIt is an Android application designed to detect, classify, and locate Bluetooth Low Energy (BTLE) devices, with a focus on identifying potentially unwanted tracking devices. [cite: 4, 7, 8] This application is intended for use by law enforcement and civilians who may be victims of stalking or similar situations. [cite: 7, 15]

## Features

* **BTLE Device Detection:** The application scans for nearby Bluetooth Low Energy devices and displays them to the user. [cite: 13, 20, 21, 22, 23, 24]
* **Device Classification:** Users can classify detected devices as safe, unknown, or suspicious. This classification data can be saved persistently. [cite: 13, 14, 25, 26, 27]
* **Device Location:** The application provides functionality to help users locate targeted BTLE devices. [cite: 13, 28, 29, 30, 31]
* **Bug Reporting:** Users can submit bug reports directly through the application to aid in development and maintenance. [cite: 13, 31, 32]

## Architecture

The application follows a Model-View-Controller (MVC) architecture, implemented using Kotlin and Jetpack Compose.

* **Model:**
    * `BtleDevice.kt`: Represents a Bluetooth Low Energy device with properties like name, address, signal strength, and safety status.
    * `BTLEDeviceEntity.kt`: Defines the Room database entity for storing device information.
    * `BTLEDeviceDao.kt`: Provides the Data Access Object (DAO) for interacting with the Room database.
    * `BtleDeviceDatabase.kt`:  Configures the Room database.
* **View:**
    * `DeviceDetailCard.kt`: Composable function to display detailed information about a single BTLE device and allow users to interact with it.
    * `DeviceListView.kt`: Composable function to display a list of detected BTLE devices.
    * `DeviceCard.kt`: Composable function to display a summary of a BTLE device in the device list.
    * `AppTopBar.kt`: Composable function for the application's top app bar.
    * `ScanButton.kt`: Composable function for the button that starts and stops the device scan.
* **Controller:**
    * `BtleViewModel.kt`: ViewModel class that manages the UI-related data and interacts with the Model.
    * `DeviceController.kt`: Handles device scanning and permission requests.
    * `LeoPermissionHandler.kt`:  Manages the requesting of necessary permissions.

## Key Technologies

* Kotlin
* Jetpack Compose
* RoomDB
* AndroidX Libraries
* Coroutines

## Current Status

The application is currently under development. Key features such as device scanning, device classification, and basic UI elements are implemented. Ongoing work focuses on:

* Improving UI/UX.
* Enhancing device filtering and sorting.
* Implementing precise device location features.
* Optimizing performance and battery usage.
* Adding more robust error handling.

## Known Issues

* Device name updates may not always be immediately reflected in the UI.
* Toggle switches in the Device Detail Card may exhibit inconsistent behavior.
* Scanning functionality and UI updates require further refinement.

## Future Work

* Implement floor plan database for storing safe/suspicious devices
* Refine the scanning logic to efficiently update device information.
* Extract and Parse UUID to identify manufacturer, device type, and use class
* Enhance the UI to provide better feedback and user guidance.
* Add mapping capabilities for device location.
* Implement user authentication and data synchronization.
* Expand compatibility to a wider range of Android versions.

## Contribution

Contributions to the project are welcome. Please follow the established code style and submit pull requests for review.

## License

MIT License

Copyright (c) 2025 Jeremy Clark

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files ("LEOFindIt"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
