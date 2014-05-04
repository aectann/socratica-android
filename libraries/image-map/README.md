#ImageMap
ImageMap is an ImageView extension which allows to define active clickable regions over an image. Pretty much
like HTML [map](http://www.w3schools.com/TAgs/tag_map.asp) tag. Default (XML) map definition format
is compatible with that of the HTML map tag. Custom formats and parsers are allowed when performance
is an issue.

##Getting binaries
The library is available via maven central repo.

####Gradle

```
compile 'com.socratica.mobile:image-map:1.+@aar'
```

####Maven

```xml
<dependency>
   <groupId>com.socratica.mobile</groupId>
   <artifactId>image-map</artifactId>
   <version>1.0.3</version>
   <type>aar</type>
</dependency>
```

##Example

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.socratica.mobile.ImageMap
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/usamap"
        app:map="@xml/usa"
        app:selectionPadding="50dp"
        app:selectionColor="#f00"
        app:selectionType="stroke"
        app:selectionStrokeWidth="2"
        android:id="@+id/map"/>

</RelativeLayout>
```

Here `app:map` attribute refers to the xml map definition:

```
<?xml version="1.0" encoding="utf-8"?>
<map >
        <!-- #$-:Image map file created by GIMP Image Map plug-in -->
        <area shape="poly" coords="271,214,385,227,380,313,260,304"  nohref="1" />
</map>
```

And the actual image is set via standard `android:src` attribute.

####Other xml attributes
 * `app:selectionPadding` - specifies how much space is left between view border and selected area when an area is clicked;
 * `app:selectionType` - `fill` or `stroke`, defines the way a selected area is highlighted;
 * `app:selectionColor` - defines highlight color;
 * `app:selectionStrokeWidth` - float, defines width of the line that is used to highlight selection, only applies if `app:selectionType` is set to `stroke`.