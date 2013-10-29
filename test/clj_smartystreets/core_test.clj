(ns clj-smartystreets.core-test
  (:use [midje.sweet])
  (:require [clj-http.client :as client]
            [clj-smartystreets.core :refer :all]))

(facts "base endpoints fetch"
  (doseq [endpoint [{:fn street-address :url street-address-url}
                  {:fn zipcode :url zipcode-url}]]
    (fact "hits the streetaddress url"
      ((:fn endpoint) {} {}) => nil
      (provided
        (client/request 
          (checker [{:keys [url]}] 
            (= url (:url endpoint)))) => nil))
    (fact "uses a get method"
      ((:fn endpoint) {} {}) => nil
      (provided
        (client/request
          (checker [{:keys [method]}]
            (= method :get))) => nil))
    (fact "passes along the provided credentials"
      ((:fn endpoint) {:auth-id "id" :auth-token "token"} {}) => nil
      (provided
        (client/request 
          (checker [{{:keys [auth-id auth-token]} :query-params}] 
            (and (= auth-id "id") 
              (= auth-token "token")))) => nil))
    (fact "passes along relevant parameters"
      ((:fn endpoint) {} {:city "Pittsburgh" :state "Pennsylvania"}) => nil
      (provided
        (client/request
          (checker [{{:keys [city state]} :query-params}]
            (and (= city "Pittsburgh")
              (= state "Pennsylvania")))) => nil))
    (fact "removes irrelevant parameters"
      ((:fn endpoint) {} {:badness "badness"}) => nil
      (provided
        (client/request
          (checker [{:keys [query-params]}]
            (nil? (:badness query-params)))) => nil))
    (fact "returns the first response from the body"
      ((:fn endpoint) {} {}) => :first
      (provided
        (client/request anything) => {:body [:first :second]}))))

(facts "zipcode->city-state"
  (fact "returns the first entry in city_states"
    (zipcode->city-state {} "15217") => {:city "Pittsburgh" :state "Pennsylvania"}
    (provided
      (client/request anything) => {:body [{:city_states [{:city "Pittsburgh" :state "Pennsylvania"}]}]})))

