# WeatherAppTestTopUpMama

## Requirements
Build a weather app using Kotlin (or java) on android using weather data from public
weather APIs, visual design to help you through here (not compulsory to use this, just be
creative):
- A screen displaying a list of current temperatures from 20 cities
- A search bar for filtering these cities
- Items from the list can be able to be made a favorite, favourite cities should go to
the top of the list
- Clicking on the items should open an item screen displaying more detailed info of
weather in that city
- A notification showing the current conditions of a favorite city at the top of the hour
(when the app is not in the foreground)
- Please ensure the app works gracefully in and out of internet activity
- Write unit tests to validate that the app works well
- Please submit an apk with a link to the codebase on GitHub

### Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - handle the stream of data asynchronously that executes sequentially.
    * [Dagger hilt](https://dagger.dev/hilt/) - a pragmatic lightweight dependency injection framework.
    * [Jetpack](https://developer.android.com/jetpack)
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library provides an abstraction layer over SQLite.
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - is an observable data holder.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
        * [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network. This approach allows your app to use both network bandwidth and system resources more efficiently. .
        * [Navigation components](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app.

    * [Glide](https://bumptech.github.io/glide/) - Glide is a fast and efficient image loading library for Android focused on smooth scrolling. Glide offers an easy to use API, a performant and extensible resource decoding pipeline and automatic resource pooling.
    * [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java

    
* CI/CD
    * Github Actions

* Architecture
    * MVVM - Model View View Model

## App Architecture
   This app uses combination Clean Architecture and MVVM architecture for separation of concerns from business logic and presentation layer integrates directly into the recommended [Android app architecture](https://developer.android.com/jetpack/guide). It majorly uses the paging library to load and display pages of data from a larger dataset from local storage or over network. This approach allows your app to use both network bandwidth and system resources more efficiently.The paging lib operates in three layers :
   
   - The repository layer
   - The ViewModel layer
   - The UI layer

     <img src="https://developer.android.com/topic/libraries/architecture/images/paging3-library-architecture.svg" />
      
      ### Repository layer

      The primary Paging library component in the repository layer is RemoteMediator. A RemoteMediator object handles paging from a layered data source, such as a network data source with a local database cache.It Defines a set of callbacks used to incrementally load data from a remote source into a local source wrapped by a PagingSource, e.g., loading data from network into a local db cache.
      
      ### ViewModel layer
      The Pager component provides a public API for constructing instances of PagingData that are exposed in reactive streams, based on a PagingSource object and a PagingConfig configuration object. The component that connects the ViewModel layer to the UI is PagingData. A PagingData object is a container for a snapshot of paginated data. It queries a PagingSource object and stores the result.
      
      ### UI layer
      
      The primary Paging library component in the UI layer is PagingDataAdapter, a RecyclerView adapter that handles paginated data.This class is a convenience wrapper around AsyncPagingDataDiffer that implements common default behavior for item counting, and listening to update events.To present a Pager, use collectLatest to observe Pager.flow and call submitData whenever a new PagingData is emitted.PagingDataAdapter listens to internal PagingData loading events as pages are loaded, and uses DiffUtil on a background thread to compute fine grained updates as updated content in the form of new PagingData objects are received.
      

## Testing

The App has tests on Fragments as well as unit tests under the Android Test packages in respective modules.
