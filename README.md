# clj-smartystreets

A Clojure library wrapping
[SmartyStreets' Cloud APIs](https://smartystreets.com/docs/cloud).

[![Build Status](https://travis-ci.org/democracyworks/clj-smartystreets.svg?branch=master)](https://travis-ci.org/democracyworks/clj-smartystreets)

## Installation

[Leiningen](https://github.com/technomancy/leiningen) dependency information:

```clojure
[democracyworks/clj-smartystreets "0.2.0"]
```

## Usage

Core functionality is provided by the `democracyworks.smartystreets.core`
namespace. Each SmartyStreets API is in its own namespace, and inclusion of API
namespaces is optional.

Require it at the REPL:

```clojure
(require '[democracyworks.smartystreets.core :as smartystreets])
;; Optional
(require '[democracyworks.smartystreets.us-street :as us-street])
;; Optional
(require '[democracyworks.smartystreets.us-zipcode :as us-zipcode])
```

**OR**

Require it in your application:

```clojure
(ns my-app.core
  (:require
   [democracyworks.smartystreets.core :as smartystreets]
   ;; Optional
   [democracyworks.smartystreets.us-street :as us-street]
   ;; Optional
   [democracyworks.smartystreets.us-zipcode :as us-zipcode]))
```

Next, you can create a client map holding your authentication information and
pass this to the API functions to call the API:

```clojure
;; Construct the client map. You can find the Auth ID and Auth Token in the
;; SmartyStreets console.
#_=> (def client
       (smartystreets/client "smartystreets-auth-id"
                             "smartystreets-auth-token"))

;; Look up a single street address.
;;
;; Note that the response is a vector, because there may be more than one
;; potential match for the given input.
#_=> (us-street/fetch-one client {:street "150 Court St"
#_=>                              :city "Brooklyn"
#_=>                              :state "New York"})

[{:delivery_line_1 "150 Court St"
  :last_line "Brooklyn NY 11201-6771"
  :delivery_point_barcode "112016771996"
  ; Response trimmed for brevity
  }]

;; Look up several street addresses.
;;
;; Note that the response is a vector of vectors, because for each input there
;; may be more than one potential match.
#_=> (us-street/fetch-many client [{:street "150 Court St"
#_=>                                :city "Brooklyn"
#_=>                                :state "New York"}
#_=>                               {:street "1600 Pennsylvania Ave NW"
#_=>                                :city "Washington"
#_=>                                :state "DC"}])

[[{:delivery_line_1 "150 Court St"
   :last_line "Brooklyn NY 11201-6771"
   ; Response trimmed for brevity
  }]
 [{:delivery_line_1 "1600 Pennsylvania Ave NW"
   :last_line "Washington DC 20500-0003"
   ; Response trimmed for brevity
  }]]
```

### Exceptions

HTTP exceptions, if thrown, are not handled by the library. See the
[Cloud API docs](https://smartystreets.com/docs/cloud) for an explanation of the
possible HTTP status codes returned by the API, and
[clj-http docs](https://github.com/dakrone/clj-http#exceptions) for an
explanation of the structure of the exceptions you may receive.

## Development

To run the tests:

```sh
$ lein test
```

## License

Copyright © 2013-2017 Democracy Works

Distributed under the Eclipse Public License, the same as Clojure.
