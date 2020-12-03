## Integration

### Gradle:
```gradle
implementation 'com.dev.hieupt:android-standalone-scroll-bar:1.0.0'
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

## Customization
- Custom track/thumb visibility by using VisibilityManager
```kotlin
scrollbar.visibilityManager = YourCustomVisibilityManager()
```
- Custom track/thumb drawable using attr scrollbarTrackDrawable and scrollbarThumbDrawable or through java/kotlin
```kotlin
scrollbar.customTrackDrawable = customDrawable
scrollbar.customThumbDrawable = customDrawable
```
- Custom thumb length by using attr scrollbarThumbLength/scrollbarThumbLengthRatio
```kotlin
scrollbar.thumbLength = desireLengthInPx
scrollbar.thumbLengthRatio = percentOfTrackLength //[0.0..1.0]
```
- Auto hide ability (attr scrollbarAutoHide)
```
scrollbar.isAutoHide = true|false
```
- Delay duration before scrollbar auto hide (attr scrollbarDelayBeforeAutoHideDuration)
```
scrollbar.delayBeforeAutoHide = delayTimeInMillis
```
