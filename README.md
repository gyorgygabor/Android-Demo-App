Android-Demo-App
============

This is our open source demo application for Android. You may re-use any code in your own project(s). If you have any questions, feel free to drop us a line at support@applovin.com.

# Instructions #

1. Log in and download the latest SDK [here](https://applovin.com/integration).

2. Copy the AppLovin JAR into your /libs folder. Make sure your `build.gradle` file is including the JAR files:

```
    compile files('libs/applovin-sdk-X.X.X.jar') // replace X.X.X with whatever version number your file is
```


3. In your AndroidManifest.xml file, add your SDK key as metadata, inside the `<application>` tag:

```
<meta-data
            android:name="applovin.sdk.key"
            android:value="YOUR_SDK_KEY_HERE" />
```

Start monetizing!
