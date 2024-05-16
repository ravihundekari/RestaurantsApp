RestaurantsApp is an android application to display list of nearby restaurants and it's details.
One can navigate to the restaurant using this application.

Tech stack
UI - Jetpack Compose
Design pattern - MVVM
Dependency Injection - Hilt
Async request and multi threading - Coroutines
Network call - Retrofit
Image Loader - Coil

Class RetrofitModule:
In Headers for Authorization, you can use this API KEY - fsq3DfAdKRH0ofX2LB45qJ4i+xPLMtWliU2dVKV1G9Oa5tU=

Pagination is not supported for the places API so it wasn't implemented.

Places API by default retrieves nearby location by using IP biasing with optional radius parameter so no additional params were sent for this.
