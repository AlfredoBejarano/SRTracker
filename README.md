# Overwatch SR Tracker
Android companion app that helps keep records of how much SR you win or lose in Overwatch

![Icon SR Tracker](./app/src/main/ic_launcher-web.png)

## This Repository

This repository contains the source code for the [Overwatch SR Tracker](https://www.google.com.mx/?gfe_rd=cr&dcr=0&ei=-7eDWu_dFuqr8we4loEY) app.

The branching strucutre is [explained here](https://gist.github.com/digitaljhelms/4287848), 
always branch off master as master reflects the latests development code.

- new features and issues must branch off master (with the naming topic-*) as master accept merges from this branches.
- Hotfixes must branch of stable (hotifx-*), as stable reflects the latest code in production and it can accept merges from hotfixes.
- topic-* branches must merge to master and deleted when work has been done, another topic-* branches must merge the master changes into them to keep working on them.


## Installation

This project requires:
- [Android Studio](https://developer.android.com/studio/index.html?hl=es-419) 3.0.1 or later.
- Android SDK 27 or later.
- Kotlin 1.2.21 or later.

### Device Requirements

This app runs in devices that meet the following requirements:

#### Minimum Requirements
- OS: Android 4.0.3 Ice Cream Sandwich 🍦 (_API 15_).
- Memory: 32 MB.
- Storage: 10 MB.
- Processor: ARMv5 @ 200 MHz.

#### Recommended Requirements
- OS: Android 8.0 Oreo© 🍪 (_API 26_).
- Memory: 50 MB.
- Storage: 12 MB.
- Processor: ARMv5 @ 420 MHz.

## Architecture

This app uses the **Model View Presenter** *(MVP)* architectural pattern
using the [@AlfredoBejarano implementation *(SimpleMVP)*](https://github.com/AlfredoBejarano/AndroidSimpleMVP) implementation.

Birefly, this implementation consist in using only three layers for a feature, those being:
- Model     - Any DTO / POJO class.
- View      - A class that *Only and only* handles data drawing and user interactions, data processing is handled by the *Presenter*.
- Presenter - This layer handles data processing, it usually runs on another thread and report this data changes in the main thread for a view to draw it.

A fourth layer is used globaly for defining where the data is located *(repository layer)*, in local database this is
accessed with a Presenter class.

In a server *(let's say, an API)* it's different, the **Repository class** is an interface that is defined using the **Retrofit API definitions**
and specifies what data the presenter needs to send.

*A view draws in the paper as the presenter handles crayons to it, if you force someone to do the other's job, you are doing it wrong*

## Project Libraries
* [Realm](https://realm.io/)

### Current App Version
```
2.0
```

## Project Licence

```
The MIT License (MIT)

Copyright (c) 2018 Commute Technologies SAPI de CV

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
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
