# clj-smartystreets

A Clojure library wrapping SmartyStreets' LiveAddress API.

## Installation

`clj-smartystreets` is available as a Maven artifact from
[Clojars](http://clojars.org/clj-smartystreets):
```clojure
[clj-smartystreets "0.1.2"]
```

## Usage

All functionality is provided by the
`clj-smartystreets.core` namespace.

Require it in the REPL:

```clojure
(require '[clj-smartystreets.core :as ss])
```

Require it in your application:

```clojure
(ns my-app.core
  (:require [clj-smartystreets.core :as ss]))
```

Currently supports the `https://api.smartystreets.com/street-address` and `https://api.smartystreets.com/zipcode`
endpoints. There is currently only support for single lookups.

```clojure
user=> (def auth {:auth-id "auth-id" :auth-token "auth-token"})
user=> (ss/street-address auth {:street "150 Court St" :city "Brooklyn" :state "New York" :zipcode "11201"})
{:input_index 0, :candidate_index 0, :delivery_line_1 "150 Court St", :last_line "Brooklyn NY 11201-6771", :delivery_point_barcode "112016771996", :components {:street_name "Court", :city_name "Brooklyn", :street_suffix "St", :zipcode "11201", :state_abbreviation "NY", :plus4_code "6771", :delivery_point "99", :primary_number "150", :delivery_point_check_digit "6"}, :metadata {:zip_type "Standard", :longitude -73.99617, :carrier_route "C034", :building_default_indicator "Y", :congressional_district "07", :county_name "Kings", :elot_sort "A", :county_fips "36047", :latitude 40.69087, :elot_sequence "0099", :record_type "H", :rdi "Commercial", :precision "Zip7"}, :analysis {:dpv_match_code "D", :dpv_footnotes "AAN1", :dpv_cmra "N", :dpv_vacant "N", :active "Y", :footnotes "H#"}}

user=> (ss/zipcode auth {:zipcode "80202"})
{:input_index 0, :city_states [{:city "Denver", :state_abbreviation "CO", :state "Colorado"}], :zipcodes [{:zipcode "80202", :zipcode_type "S", :county_fips "08031", :county_name "Denver", :latitude 39.747778, :longitude -104.993838}]}
```



### Exceptions

HTTP exceptions are currently ignored by the library and passed on to the end user. See the LiveAddress
API docs for an explanation of the received exceptions.

```clojure
user=> (def auth {:bad "stuff"})
#'user/auth
user=> (ss/zipcode auth {:zipcode "80202"})

ExceptionInfo clj-http: status 401  clj-http.client/wrap-exceptions/fn--2764 (client.clj:147)
```

## Development

To run the tests:

    $ lein test

## License

Copyright (C) 2013 Democracy Works

Distributed under the Eclipse Public License, the same as Clojure.
