(ns smartystreets-clj.core
  (:require [clj-http.client :as client]
            [clojure.string :refer [join blank?]]))

(def street-address-url "https://api.smartystreets.com/street-address")
(def zipcode-url "https://api.smartystreets.com/zipcode")

(def street-address-request-fields [:street :street2 :secondary :city :state :zipcode :lastline :addressee :urbanization])
(def zipcode-request-fields [:city :state :zipcode])

(defn- do-request
  "make a call to the smartystreets service"
  ;{:method GET :url URL :query-params QUERY-PARAMS}
  [url auth query-params]
  (client/get url {:as :json :query-params (merge query-params auth)}))


(defn- fetch
	[endpoint fields]
	(fn 
		[auth address-params]
	  (->> (select-keys address-params fields)
	  	(do-request endpoint auth)
	  	:body
	  	first)))

(def street-address
	(fetch street-address-url street-address-request-fields))

(def zipcode
	(fetch zipcode-url zipcode-request-fields))

(defn zipcode->city-state
	[auth zipcode]
	(-> (zipcode auth {:zipcode zipcode})
		  :city_states
		  first))
