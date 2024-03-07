# Delivery fee calculator

## About

#### This small application uses Estonian Environment Agency's weather observation data to calculate additional delivery fee for certain weather conditions

#### Observation endpoint: `https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php`

#### Observation documentation: `https://www.ilmateenistus.ee/teenused/ilmainfo/eesti-vaatlusandmed-xml/`

## API endpoints

### Getting fee for specific city and vehicle type
Calculated fee depends on city, vehicle type and weather.
Scooter and bike have extra fees based on weather and
dangerous weather phenomenon like thunder forbid their usage.

#### Getting fees for Tallinn and car:
```
GET request: http://localhost:8080?city=Tallinn&vehicleType=car
Response example: 4.0
```

###### Note: city and vehicleType parameters are required and are case-sensitive!

Acceptable city parameters:
```
Tallinn
Tartu
Pärnu
```

Acceptable vehicleType parameters: 
```
car
scooter
bike
```

## Management endpoints
Management endpoints allows us to get and change current
fees, view, add and remove weather stations.

### Weather fees endpoint
There are two use cases for getting weather fees.
First one is to get all current weather fees and
second is to only get fee for specific weather condition.

#### Getting all weather fees example:
```
GET request: http://localhost:8080/manage/getWeatherFee
Response example: {Air temperature less than -10=1.0 Air temperature -10 to 0=0.5 Wind speed 10 to 20 m/s=0.5 Snow or sleet=1.0 rain=0.5}
```

#### Getting fee for when air temperature is under -10 degrees example:
```
GET request: http://localhost:8080/manage/getWeatherFee?weather=atef_lessThanMinusTen
Response example: 1.0
```

Full list of weather parameters and their meaning:
```
atef_lessThanMinusTen   -> air temperature is less than -10 degrees
atef_minusTenToZero     -> air temperature is between -10 and 0 degrees
wsef_tenToTwentyMps     -> wind speed is 10-20 m/s
wpef_snowOrSleet        -> weather phenomenon is snow or sleet
wpef_rain               -> weather phenomenon is rain related
```

### Setting weather fees

#### Set rain related weather fee to 1.5 example:
```
POST request: http://localhost:8080/manage/setWeatherFee?weather=wpef_rain&fee=1.5
Response example: Weather fees updated
```
###### Note: use 1.5 instead of 1,5! No commas.

### Regional fees endpoint

#### Get regional fee for every city and vehicle type example:
```
GET request: http://localhost:8080/manage/getRegionalFee
Response example:
{city=Tallinn car=4.0 scooter=3.5 bike=3.0}
{city=Tartu car=3.5 scooter=3.0 bike=2.5}
{city=Pärnu car=3.0 scooter=2.5 bike=2.0}
```

#### Get regional fee for Pärnu example:
```
GET request: http://localhost:8080/manage/getRegionalFee?city=Pärnu
Response example: {city=Pärnu car=3.0 scooter=2.5 bike=2.0}
```

#### Get regional fee for Tartu with scooter as vehicle type example:
```
GET request: http://localhost:8080/manage/getRegionalFee?city=Tartu&vehicleType=scooter
Response example: 3.0
```

#### Set regional fee example:
```
POST request: http://localhost:8080/manage/setRegionalFee?city=Tartu&vehicleType=bike&fee=1.5
Response example: Regional fee updated
```

### Weather stations endpoint

#### Get list of cities and weather stations example:
```
GET request: http://localhost:8080/manage/getWeatherStations
Response example: {city=Tallinn, station=Tallinn-Harku}{city=Tartu, station=Tartu-Tõravere}{city=Pärnu, station=Pärnu}
```

#### Add weather station example:
```
POST request: http://localhost:8080/manage/addWeatherStation?city=Tallinn&station=Tallinn-Harku
Response example: Added weather station: Tallinn - Tallinn-Harku
```

#### Remove weather station example:
```
DELETE request: http://localhost:8080/manage/removeWeatherStation?city=Tallinn&station=Tallinn-Harku
Response example: Removed weather station: Tallinn - Tallinn-Harku
```