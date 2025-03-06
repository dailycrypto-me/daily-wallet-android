# DailyWallet - Daily movile wallet for Android

# Getting Started

1. [Download](https://developer.android.com/studio/) Android Studio.
2. Clone this repository
3. Obtain a free Infura API key and replace the one in build.gradle
4. Generate a GitHub [Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) with `read:packages, read:user` permission
5. Edit `~/.gradle/gradle.properties` add blow properties:
```properties
gpr.user=Your GitHub Email
gpr.key=The GitHub Personal Access Token you created in previous step
```
6. Build the project in AndroidStudio or Run `./gradlew build` to install tools and dependencies. See [BUILD.md](BUILD.md) for more details.

You can also build it from the commandline just like other Android apps. Note that JDK 17 is the version now supported by Android. We build with the JetBrains JDK.