# Compose-SpinKit
Add loading spinners in Android Jetpack Compose.  
> Implementation of [SpinKit](https://tobiasahlin.com/spinkit/) with additionals.

[Demo Apk](https://raw.githubusercontent.com/ocnyang/compose-spinkit/master/res/app-debug.apk)

## Preview


<div align=center>
<img src="https://raw.githubusercontent.com/ocnyang/compose-spinkit/master/res/demo.gif" width="300px" alt="preview"/>
<img src="https://cdn.jsdelivr.net/gh/ocnyang/compose-spinkit@master/res/demo_colorful.gif" width="300px" alt="preview-colorful"/>
</div>

## Gradle Dependency

```groovy
dependencies {
    implementation 'com.github.OCNYang:Compose-SpinKit:1.0'
}
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