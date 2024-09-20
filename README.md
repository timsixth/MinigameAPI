# MinigameAPI
Flexible, modern and easy to use library to creating minigames in minecraft. <br>
Library works from 1.13.2 to 1.21.x.
<br>
Please report every bugs in issues section.<br>

JavaDocs: https://timsixth.pl/javadocs/minigame_api/2.0.0/ <br>
Documentation: https://timsixths-plugins.gitbook.io/minigameapi-docs/

## Main library features:
- Arena management system
- Game management system
- Game cosmetics system
- Teams in-game
- Advanced loading system from YAML files 
- Game statistics system
- Coins system
- Simple minigame configuration
- Simple saving, deleting and updating system (You don't have to write a query to a database or use YAML spigot API to execute these actions)
- Modules system (You can install modules, which are add more functionality)
- Rejoin feature

## Features which are included via modules:
- Advanced loading system from SQL databases (MySQL or SQLite)
- Integration with T-DataBasesAPI (Thanks to this plugin management of SQL databases is really simple) 
- Commands API (You can create parent commands and subcommands in separate classes)
- MongoDb support

## Getting started

> [!IMPORTANT]
> {VERSION} - current version form GitHub releases section:<br>
> https://github.com/timsixth/MinigameAPI/releases

Maven
```xml
<repositories>
  <repository>
   <id>jitpack.io</id>
   <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>api</artifactId>
   <version>{VERSION}</version>
</dependency>
```

Gradle
```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
  
 dependencies {
   implementation 'com.github.timsixth.MinigameAPI:api:{VERSION}' 
}
```

Other modules (Maven)
```xml
<!-- library module includes addon system, api, addons-api and runnable plugin-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>library</artifactId>
   <version>{VERSION}</version>
</dependency>

<!-- library-lite module includes only api and runnable plugin-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>library-lite</artifactId>
   <version>{VERSION}</version>
</dependency>

<!-- addons-api module includes only api and addons-api-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>addons-api</artifactId>
   <version>{VERSION}</version>
</dependency>

<!-- example-minigame module includes example usage of minigame-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>example-minigame</artifactId>
   <version>{VERSION}</version>
</dependency>
```

Other modules (Gradle)
```gradle  
 dependencies {
   //includes all modules
   implementation 'com.github.timsixth:MinigameAPI:{VERSION}'
   //includes only library, api, and addons-api modules
   implementation 'com.github.timsixth.MinigameAPI:library:{VERSION}'
   //includes only api and library-lite modules
   implementation 'com.github.timsixth.MinigameAPI:library-lite:{VERSION}'   
   //includes only api and addons-api modules
   implementation 'com.github.timsixth.MinigameAPI:addons-api:{VERSION}' 
   //includes example-minigame module
   implementation 'com.github.timsixth.MinigameAPI:example-minigame:{VERSION}'    
}
```
