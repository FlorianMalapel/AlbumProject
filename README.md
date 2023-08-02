
# AlbumProject

Android app that show a list of album. 




## More context

I received an endpoint which gave me a list of object like this: 

```json
[
    {
        "albumId": 1,
        "id": 1,
        "title": "accusamus beatae ad facilis cum similique qui sunt",
        "url": "https://via.placeholder.com/600/92c952",
        "thumbnailUrl": "https://via.placeholder.com/150/92c952"
    },
    {
        "albumId": 1,
        "id": 2,
        "title": "reprehenderit est deserunt velit ipsam",
        "url": "https://via.placeholder.com/600/771796",
        "thumbnailUrl": "https://via.placeholder.com/150/771796"
    },
    {
        "albumId": 1,
        "id": 3,
        "title": "officia porro iure quia iusto qui ipsa ut modi",
        "url": "https://via.placeholder.com/600/24f355",
        "thumbnailUrl": "https://via.placeholder.com/150/24f355"
    },
    {
        "albumId": 2,
        "id": 4,
        "title": "officia porro iure quia iusto qui ipsa ut modi",
        "url": "https://via.placeholder.com/600/24f355",
        "thumbnailUrl": "https://via.placeholder.com/150/24f355"
    }
    ...
]
```

The instruction was to create an android app that display a list of album using this dataset. At first look, we saw that several objects have the same `albumId`, which means that the objects of the list represents tracks of an album.
So I decided to start building an app that get a list of tracks, and from that I will display them sorted by albums. 


## Choices

**Architecture:** Mvvm + Simple Clean Architecture\
This kind of architecture may be a little more longer to setup than others but it has a lot of advantages. It allow to separate logic from ui, to test each parts easier, and to organise your code and data in several level which is very useful once you handle it.

**UI:** Full Jetpack compose\
Compose allow to build views a lot faster than the old XML way of building it, it increase also the reusability of the views created.

**Language**: Full Kotlin with corountines for the async side.

**Librairies**\
    • **Room database**: Allow to easily handle database, great fit with the clean architecture data model\
    • **Retrofit**: Fit well with kotlin coroutines and the clean architecture, allow to write code using interface, a little like the way of using room database. This way have a similarity of used and have a nice and clean code :)\
    • **Moshi**: Works great with retrofit and simplifies data parsing, here also, similarity of use with room database\
    • **Coil**: Image loader that works with compose, really usefull it will handle image loading and cache them to still have them offline\



## Screenshots

The app has a simple design: 
![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-114052.png?raw=true)


![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-114109.png?raw=true)


It can handle network connexion changes:
![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-114037.png?raw=true)

Large screen compatibility:
![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-113418.png?raw=true)


![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-113425.png?raw=true)


Landscape configuration compatibility:
![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-113651.png?raw=true)


![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-113636.png?raw=true)


Dark mode configuration compatibility:
![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-113900.png?raw=true)


![App Screenshot](https://github.com/FlorianMalapel/AlbumProject/blob/feature/unitTests/screenshots/Screenshot_20230802-113907.png?raw=true)




## Tests
Both `Album`and `Track` are tested using `junit` to be sure that the data are well pass from a level to an other of the clean architecture.

An other test try a basic journey of the app, the be sure that the data are well received, displayed. 



