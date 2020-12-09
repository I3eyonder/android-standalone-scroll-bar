
## Integration

### Gradle:
```gradle
implementation 'com.dev.hieupt:android-standalone-scroll-bar:1.1.0'
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
To using with [`NestedScrollView`, `ScrollView`, `HorizontalScrollView`, `WebView`], please use [[`NestedScrollView2`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/NestedScrollView2.kt), [`ScrollView2`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/ScrollView2.kt), [`HorizontalScrollView2`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/HorizontalScrollView2.kt), [`WebView2`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/view/WebView2.kt)] for your layout instead.
```kotlin
scrollbar.attachTo(nestedScrollView2)
```
You can also implement your own [`ScrollableView`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/ScrollableView.kt) to use [`StandaloneScrollBar`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/StandaloneScrollBar.kt) with any `View`. You can refer [`VerticalScrollViewHelper`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/viewhelper/VerticalScrollViewHelper.kt) for example of implementation.
```kotlin
scrollbar.attachTo(scrollableView)
```

## Customization
- Custom track/thumb visibility by using [`VisibilityManager`](https://github.com/hieupham1993/android-standalone-scroll-bar/blob/master/android-standalone-scroll-bar/src/main/java/com/hieupt/android/standalonescrollbar/VisibilityManager.kt)
```kotlin
scrollbar.visibilityManager = YourCustomVisibilityManager()
```
- Custom track/thumb drawable using attribute `scrollbarTrackDrawable` and `scrollbarThumbDrawable` or through java/kotlin
```kotlin
scrollbar.customTrackDrawable = customDrawable
scrollbar.customThumbDrawable = customDrawable
```
- Tint track/thumb by using attribute `scrollbarDefaultTrackTint` and `scrollbarDefaultThumbTint`
```kotlin
scrollbar.defaultThumbTint = ColorStateList()
scrollbar.defaultTrackTint = ColorStateList()
```
- Custom thumb length by using attribute `scrollbarThumbLength`/`scrollbarThumbLengthRatio`
```kotlin
scrollbar.thumbLength = desireLengthInPx
scrollbar.thumbLengthRatio = percentOfTrackLength //[0.0..1.0]
```
- Enable/Disable thumb bar drag ability using attribute `scrollbarDraggable`
```
scrollbar.draggable= true|false
```
- Auto hide ability (attribute `scrollbarAlwaysShow`)
```
scrollbar.isAlwaysShown= true|false
```
- Delay duration before scrollbar auto hide (attribute `scrollbarDelayBeforeAutoHideDuration`)
```
scrollbar.delayBeforeAutoHide = delayTimeInMillis
```
