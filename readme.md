# Compose-SpinKit
Add loading spinners in Android Jetpack Compose.  
> Implementation of [SpinKit](https://tobiasahlin.com/spinkit/) with additionals.

[Demo Apk](https://raw.githubusercontent.com/ocnyang/compose-spinkit/master/res/app-debug.apk)

## Preview


<div align=center>
<img src="https://raw.githubusercontent.com/ocnyang/compose-spinkit/master/res/demo.gif" width="300px" alt="preview"/>
<img src="https://cdn.jsdelivr.net/gh/ocnyang/compose-spinkit@master/res/demo_colorful.gif" width="300px" alt="preview-colorful"/>
</div>

## Gradle Dependency [![](https://jitpack.io/v/OCNYang/Compose-SpinKit.svg)](https://jitpack.io/#OCNYang/Compose-SpinKit)

```groovy
dependencies {
    implementation("com.github.OCNYang.Compose-SpinKit:library:1.0.5") {
        exclude("com.github.jitpack")
    }
}
```

all jitpack build
```
✅ Build artifacts:
com.github.OCNYang.Compose-SpinKit:library-desktop:1.0.5
com.github.OCNYang.Compose-SpinKit:library-android-debug:1.0.5
com.github.OCNYang.Compose-SpinKit:library-android:1.0.5
com.github.OCNYang.Compose-SpinKit:library:1.0.5
com.github.OCNYang.Compose-SpinKit:compose-spinkit:1.0.5
```

## Usage

```kotlin
@Composable
fun LoadingBox() {
    Rainbow() // Attribute configuration is very simple, directly look at the source code;
}
```


## Acknowledgements
- [ComposeLoading](https://github.com/commandiron/ComposeLoading) (fork by this project).
