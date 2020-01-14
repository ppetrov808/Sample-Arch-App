# Sample-Arch-App

Sample App that implements Clean Architecture on Android.

App contains 3 modules that are the main layers of [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html): 
* **data** - data layer, contains data object models, repositories, stores (work with db, net, cache)
* **domain** - domain layer, contains business logic, use case classes (interactors), entity model classes
* **presentation** - presentation layer, contains app UI classes and also implements [Google MVVM](https://developer.android.com/jetpack/docs/guide) architecture.
