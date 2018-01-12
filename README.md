# RxIntent
[![](https://jitpack.io/v/1van/RxIntent.svg)](https://jitpack.io/#1van/RxIntent)

This library using `startActivityForResult` or start Camera App without callback.  
Support Android N(FileUriExposedException).

## Setup

To use this library your `minSdkVersion` must be >= 16. just because of not to be old school developer!

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency:
```gradle
dependencies {
    implementation 'com.github.1van:RxIntent:-SNAPSHOT'
}

```

## Usage

```java
RxIntent.startActivity(ExampleActivity.this, ResultActivity.class)
        .subscribe(result -> {
            // TODO
         });
```

```java
RxIntent.with(this)
        .intent(new Intent(ExampleActivity.this, ResultActivity.class))
        .startActivity()
        .subscribe(result -> {
            // TODO
        });
```

Capture videos:
```java
RxIntent.with(this)
        .video()
        .setDurationLimit(10)
        .setVideoQuality(1)
        .file() // Provide two return types are File and Uri.
        .subscribe(file -> {
            // TODO
        });
```

Capture images:
```java
RxIntent.with(this)
        .image()
        .uri() // Provide two return types are File and Uri.
        .subscribe(uri -> {
            // TODO
        });
```

With [RxPermissions](https://github.com/tbruyelle/RxPermissions):
```java
RxPermissions rxPermissions = new RxPermissions(this);
rxPermissions.request(Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .flatMap(granted -> RxIntent.with(this).image().uri())
        .subscribe(uri -> {
            // TODO 
        });
```
