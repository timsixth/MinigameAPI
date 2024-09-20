# MongoDB Module

> [!CAUTION]
> Module must be shaded to your JAR file <br>

> [!WARNING]
> The documenation is not completed, because this module is in Alpha version.<br>
> Please ask for help to developer on [discord](https://discord.com/invite/zKmMy8bK56)

## Adding dependencies

```xml
<repositories>
  <repository>
   <id>jitpack.io</id>
   <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>mongodb-support</artifactId>
   <version>v2.0.0</version>
</dependency>
```

```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
  
 dependencies {
   implementation 'com.github.timsixth.MinigameAPI:mongodb-support:v2.0.0'    
}
```

## How to register MongoDb module?

```java
    //Use MongoDbLibraryConfiguration instend of LibraryConfiguration
    //Type list with MongoDbModule
    @Override
    protected LibraryConfiguration configure() {
        return new MongoDbLibraryConfiguration(this, getConfiguratorsInitializer())
                .builder()
                .setGameManager(new MyGameManager())
                .registerModules(new MongoDbModule())
                .build();
    }
```

with custom MongoDb module configuration (Optional but recommended)
```java
    @Override
    protected LibraryConfiguration configure() {
        return new MongoDbLibraryConfiguration(this, getConfiguratorsInitializer())
                .builder()
                .setGameManager(new MyGameManager())
                .registerModules(new MongoDbModule(new MyMongoDbModuleConfigurator().configure()))
                .build();
    }
```

## How to define MongoDb Module configurator (Optional but recommended)?

```java
public class MyMongoDbModuleConfigurator extends DefaultMongoDbModuleConfigurator {

   @Override
    public MongoDbModuleConfiguration configure() {
        return MongoDbModuleConfiguration.builder()
                .mongoDbUri("mongodb://localhost:27017") //you can change this
                .databaseName("minigameapi") //you can change this
                .build();
    }
}
```
