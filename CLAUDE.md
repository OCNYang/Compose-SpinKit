# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Compose-SpinKit is an Android Jetpack Compose library providing 23 animated loading spinners based on [SpinKit](https://tobiasahlin.com/spinkit/). Forked from [ComposeLoading](https://github.com/commandiron/ComposeLoading).

## Build Commands

```bash
# Build everything
./gradlew build

# Build library only
./gradlew :library:build

# Build and install demo app
./gradlew :app:installDebug

# Run tests
./gradlew test
./gradlew :app:connectedAndroidTest  # instrumentation tests
```

## Architecture

**Multi-module Gradle project:**
- `:library` - The distributable spinner library (published to JitPack as `com.github.OCNYang:Compose-SpinKit`)
- `:app` - Demo application showcasing all spinners with interactive controls

**Library structure:**
- `library/src/main/java/com/ocnyang/compose_loading/` - 23 spinner composables (e.g., `Rainbow.kt`, `CubeGrid.kt`, `Pulse.kt`)
- `library/src/main/java/com/ocnyang/compose_loading/transition/Transition.kt` - Shared animation utility

**Animation system:**
All spinners use `InfiniteTransition.fractionTransition()` for keyframe-based infinite animations. Each spinner is a `@Composable` function with consistent parameters:
- `size: Dp` - spinner dimensions (typically defaults to 40.dp)
- `durationMillis: Int` - animation cycle duration (typically 2000ms)
- `colors: List<Color>` - spinner colors (defaults to MaterialTheme.colorScheme.primary)

**Adding a new spinner:**
1. Create a new `.kt` file in `library/src/main/java/com/ocnyang/compose_loading/`
2. Follow the pattern: `@Preview @Composable fun SpinnerName(modifier, size, durationMillis, colors, ...)`
3. Use `rememberInfiniteTransition()` with `fractionTransition()` for animations
4. Draw with `Canvas` composable

## Tech Stack

- Kotlin with JVM target 17
- Compose BOM 2024.02.00, Compiler 1.5.1
- Min SDK 21, Target SDK 34