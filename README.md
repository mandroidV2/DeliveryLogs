# DeliveryLogs

This project uses Google Maps, please find google_maps_api.xml file to see how to generate api key for google maps.

Project contains : 

1. List of deliveries.
2. Delivery Details with Location marker on Google Map


Tech stack : 
 - Simple HttpRequest to get the api data with limit->20 
 - Universal image loader library to render image
 - Shared pref to save the api data to render in offline mode
 - Model View Presenter (MVP) design pattern
 - Gson library to parse json data
