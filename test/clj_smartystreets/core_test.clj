(ns clj-smartystreets.core-test
  (:use [midje.sweet])
  (:require [clj-http.client :as client]
            [clj-smartystreets.core :refer :all]
            [cheshire.core :refer [parse-string]]))

(facts "base endpoints fetch"
       (doseq [endpoint [{:fn street-address :url street-address-url}
                         {:fn zipcode :url zipcode-url}]]
         (fact "hits the streetaddress url"
               ((:fn endpoint) {} {}) => nil
               (provided
                (client/request
                 (checker [{:keys [url]}]
                          (= url (:url endpoint)))) => nil))
         (fact "uses a post method"
               ((:fn endpoint) {} {}) => nil
               (provided
                (client/request
                 (checker [{:keys [method]}]
                          (= method :post))) => nil))
         (fact "passes along the provided credentials"
               ((:fn endpoint) {:auth-id "id" :auth-token "token"} {}) => nil
               (provided
                (client/request
                 (checker [{{:keys [auth-id auth-token]} :query-params}]
                          (and (= auth-id "id")
                               (= auth-token "token")))) => nil))
         (fact "passes along relevant parameters in the body"
               ((:fn endpoint) {} {:city "Pittsburgh" :state "Pennsylvania"}) => nil
               (provided
                (client/request
                 (checker [{body :body}]
                          (let [{:keys [city state]} (first (parse-string body true))]
                            (and (= city "Pittsburgh")
                                 (= state "Pennsylvania"))))) => nil))
         (fact "removes irrelevant parameters"
               ((:fn endpoint) {} {:badness "badness"}) => nil
               (provided
                (client/request
                 (checker [{:keys [body]}]
                          (nil? (:badness (parse-string body true))))) => nil))
         (fact "returns the match in the body"
               ((:fn endpoint) {} {}) => {:input_index 0}
               (provided
                (client/request anything) => {:body [{:input_index 0} {:input_index 1}]}))))

(facts "zipcode->city-state"
       (fact "returns the match entry in city_states"
             (zipcode->city-state {} "15217") => {:city "Pittsburgh" :state "Pennsylvania"}
             (provided
              (client/request anything) => {:body [{:input_index 0 :city_states [{:city "Pittsburgh" :state "Pennsylvania"}]}]})))
