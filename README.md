# MinigameAPI
Flexible, modern and easy to use library to creating minigames in minecraft. <br>
Library works from 1.13.2 to 1.20.x.
<br>
Please report every bugs in issues section.<br>

JavaDocs: https://timsixth.pl/javadocs/minigame_api/1.2.0/ <br>
Documentation: https://timsixths-plugins.gitbook.io/minigameapi-docs/

## Library features:
- Arena management system
- Game management system
- Game cosmetics system
- Teams in game
- Advanced loading system from SQL databases (MySQL or SQLite) or YAML files
- Game statistics system
- Coins system
- Simple minigame configuration
- Integration with T-DataBasesAPI (Thanks to this plugin management of SQL databases is really simple)
- Simple saving, deleting and updating system (You don't have to write query to database or use YAML spigot API to execute these actions)
- Commands API (You can create parent commands and subcommands in separate classes)
- Addons system (You can create addon will works with every mini game which is using this library)

## Addons
- [Boosters](https://github.com/timsixth/BoostersMiniGameAddon)

## Getting started

Maven
```xml
<repositories>
  <repository>
   <id>jitpack.io</id>
   <url>https://jitpack.io</url>
  </repository>
</repositories>
<!-- library module includes addon system, api, addons-api and runnable plugin-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>library</artifactId>
   <version>Tag</version>
</dependency>

<!-- library-lite module includes only api and runnable plugin-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>library-lite</artifactId>
   <version>Tag</version>
</dependency>

<!-- api module includes only api and T-DataBasesAPI (It's recommended to use this module 
to create the minigame)-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>api</artifactId>
   <version>Tag</version>
</dependency>

<!-- addons-api module includes only api and addons-api-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>addons-api</artifactId>
   <version>Tag</version>
</dependency>

<!-- example-minigame module includes example usage of minigame-->
<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>example-minigame</artifactId>
   <version>Tag</version>
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
   //includes all modules
   implementation 'com.github.timsixth:MinigameAPI:Tag'
   //includes only library, api, and addons-api modules
   implementation 'com.github.timsixth.MinigameAPI:library:Tag'
   //includes only api and library-lite modules
   implementation 'com.github.timsixth.MinigameAPI:library-lite:Tag'   
   //includes only api module
   implementation 'com.github.timsixth.MinigameAPI:api:Tag' 
   //includes only api and addons-api modules
   implementation 'com.github.timsixth.MinigameAPI:addons-api:Tag' 
   //includes example-minigame module
   implementation 'com.github.timsixth.MinigameAPI:example-minigame:Tag' 
   
}
```
