(ns smartystreets-clj.core-test
	(:use [midje.sweet])
  (:require [clj-http.client :as client]
            [smartystreets-clj.core :refer :all]))

(facts "streetaddress fetch"
	(fact "hits the streetaddress url"
		(street-address {} {}) => nil
		(provided
			(client/request 
				(checker [{:keys [url]}] 
					(= url street-address-url))) => nil))
	(fact "uses a get method"
		(street-address {} {}) => nil
		(provided
			(client/request
				(checker [{:keys [method]}]
					(= method :get))) => nil))
	(fact "passes along the provided credentials"
		(street-address {:auth-id "id" :auth-token "token"} {}) => nil
		(provided
			(client/request 
				(checker [{{:keys [auth-id auth-token]} :query-params}] 
					(and (= auth-id "id") 
						   (= auth-token "token")))) => nil))
	(fact "passes along relevant parameters"
		(street-address {} {:city "Pittsburgh" :state "Pennsylvania"}) => nil
		(provided
			(client/request
				(checker [{{:keys [city state]} :query-params}]
					(and (= city "Pittsburgh")
						   (= state "Pennsylvania")))) => nil))
	(fact "returns the first response from the body"
		(street-address {} {}) => :first
		(provided
			(client/request anything) => {:body [:first :second]}))

)
