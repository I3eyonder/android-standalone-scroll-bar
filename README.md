## Demo
<img src="demo/demo-vertical-recycler-view.gif" width="400" />

## Integration

### Gradle:

This library is available on [jitpack.io](https://jitpack.io/#I3eyonder/android-standalone-scroll-bar).

###### Step 1. Add the JitPack repository to your build file

Add it in your settings.gradle.kts at the end of repositories:

```gradle
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url = uri("https://jitpack.io") }
	}
}
```

###### Step 2. Add the dependency

[![](https://jitpack.io/v/I3eyonder/android-standalone-scroll-bar.svg)](https://jitpack.io/#I3eyonder/android-standalone-scroll-bar)

```gradle
implementation("com.github.I3eyonder:android-standalone-scroll-bar:Tag")
```

## Usage
#### Xml

```
<androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

<com.hieupt.android.standalonescrollbar.StandaloneScrollBar
        android:id="@+id/scrollbar"
        android:layout_width="wrap_content"
        android:layout_height="match_parent" />
```
#### Code
```kotlin
scrollbar.attachTo(recyclerView)
```
To using with [`NestedScrollView`, `ScrollView`, `HorizontalScrollView`, `WebView`], please use [[`NestedScrollView2`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/NestedScrollView2.kt), [`ScrollView2`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/ScrollView2.kt), [`HorizontalScrollView2`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/HorizontalScrollView2.kt), [`WebView2`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/WebView2.kt)] for your layout instead.
```kotlin
scrollbar.attachTo(nestedScrollView2)
```
You can also implement your own [`ScrollableView`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/ScrollableView.kt) to use [`StandaloneScrollBar`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/StandaloneScrollBar.kt) with any `View`. You can refer [`VerticalScrollViewHelper`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/viewhelper/VerticalScrollViewHelper.kt) or [`HorizontalScrollViewHelper`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/viewhelper/HorizontalScrollViewHelper.kt) for example of implementation.
```kotlin
scrollbar.attachTo(scrollableView)
```

## Customization
- Custom track/thumb visibility by using [`VisibilityManager`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/visibilitymanager/VisibilityManager.kt).
    
    Library comes with 2 built-in visibility managers: [`SimpleVisibilityManager`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/visibilitymanager/SimpleVisibilityManager.kt) and [`FadeVisibilityManager`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/visibilitymanager/FadeVisibilityManager.kt). [`FadeVisibilityManager`](android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/visibilitymanager/FadeVisibilityManager.kt) is used as default one.
```kotlin
scrollbar.visibilityManager = YourCustomVisibilityManager()
```
**Attention:**
1. The logic for showing/hiding of a `VisibilityManager` is implemented differently, so changing the `VisibilityManager` midway can lead to conflicts. Therefore, to avoid potential errors, the `VisibilityManager` must be set before the scrollbar is attached to the view.
2. Track/thumb should not be hidden using the `GONE` view. Doing so may affect the calculation of the track/thumb’s size and position. Instead, using `INVISIBLE` is a better approach.

---
- Custom track/thumb drawable using attribute `scrollbarTrackDrawable` and `scrollbarThumbDrawable` or through java/kotlin
```kotlin
scrollbar.customTrackDrawable = customDrawable
scrollbar.customThumbDrawable = customDrawable
```
---
- Tint track/thumb by using attribute `scrollbarDefaultTrackTint` and `scrollbarDefaultThumbTint`
```kotlin
scrollbar.defaultThumbTint = ColorStateList()
scrollbar.defaultTrackTint = ColorStateList()
```
---
- Custom thumb length by using attribute `scrollbarThumbLength`|`scrollbarThumbLengthByTrackRatio`|`scrollbarMinThumbLength`|`scrollbarAutoThumbLength`. If multi attributes is set, the priorities order will be `scrollbarThumbLength` > `scrollbarThumbLengthByTrackRatio` > `scrollbarAutoThumbLength`
```kotlin
scrollbar.thumbLength = desireLengthInPx
scrollbar.thumbLengthByTrackRatio = percentOfTrackLength //[0.0..1.0]
scrollbar.minThumbLength = desireLengthInPx
scrollbar.autoThumbLength = true|false
```
---
- Enable/Disable thumb bar drag ability using attribute `scrollbarDraggable`
```kotlin
scrollbar.draggable = true|false
```
---
- Auto hide ability (attribute `scrollbarAlwaysShow`)
```kotlin
scrollbar.isAlwaysShown = true|false
```
---
- Delay duration before scrollbar auto hide (attribute `scrollbarDelayBeforeAutoHideDuration`)
```kotlin
scrollbar.delayBeforeAutoHide = delayTimeInMillis
```
