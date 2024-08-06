# SQL Module

## How to register SQL module?

```java
    //Use SQLLibraryConfiguration instend of LibraryConfiguration
    //Type list with SQLModule
    @Override
    protected LibraryConfiguration configure() {
        return new SQLLibraryConfiguration(this, getConfiguratorsInitializer(),
                () -> Collections.singletonList(new SQLModule())) //you can register modules before others
                .builder()
                .setGameManager(new MyGameManager(this, settings, messages))
                .build();
    }
```
