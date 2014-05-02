#About this project

The project contains set of useful Android libraries used by [Socratica](http://socratica.com) to develop
android applications.

Right now there are four libraries:

 1. big-image - extension of Android ImageView to support pinch-zoom and pane gestures.
 2. image-map - extension of the big-image which allow defining active regions over an image (like in HTML image maps). Supports HTML image map definition format.
 3. typeface - a set of Android TextView based views to allow easy xml configuration for using non standard fonts. App-wide configuration is available.
 4. misc - all other code with no specific grouping.

## Getting binaries

The libraries are available via maven central repo. If you use gradle, you can add typeface library like this:

```
    compile 'com.socratica.mobile:typeface:1.+@aar'
```

If you have a maven build, you'll need something like this:

```xml
 <dependency>
       <groupId>com.socratica.mobile</groupId>
       <artifactId>typeface</artifactId>
       <version>1.0.1</version>
       <type>aar</type>
 </dependency>
```

Snapshots are available at [Sonatype OSS snapshots maven repo](https://oss.sonatype.org/content/repositories/snapshots/com/socratica/mobile/).

#To contributors

Read [gradle-mvn-push](https://github.com/chrisbanes/gradle-mvn-push) readme to understand library release process.

To release a library run following from the library dir:

```
../../gradlew clean build uploadArchives
```
##Useful links
1. [Sonatype OSS Maven Repository Usage Guide](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-7a.DeploySnapshotsandStageReleaseswithMaven)

#License
    Copyright 2014 Socratica LLC.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

