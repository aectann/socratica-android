#About this project

The project contains set of useful Android libraries used by [Socratica](http://socratica.com) to develop
android applications.

Right now there are four libraries:

 1. big-image - extension of Android ImageView to support pinch-zoom and pane gestures.
 2. image-map - extension of the big-image which allow defining active regions over an image (like in HTML image maps). Supports HTML image map definition format.
 3. typeface - a set of Android TextView based views to allow easy xml configuration for using non standard fonts. App-wide configuration is available.
 4. misc - all other code with no specific grouping.

## Getting binaries

The libraries are available at [Sonatype OSS snapshots maven repo](https://oss.sonatype.org/content/repositories/snapshots/com/socratica/mobile/).

#To contributors

Read [gradle-mvn-push](https://github.com/chrisbanes/gradle-mvn-push) readme to understand library release process.

To release a library run following from the library dir:

```
../../gradlew clean build uploadArchives
```
##Useful links
1. [Sonatype OSS Maven Repository Usage Guide](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-7a.DeploySnapshotsandStageReleaseswithMaven)
