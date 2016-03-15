(ns clj-smartystreets.core
  (:require [clj-http.client :as client]
            [clojure.string :refer [join blank?]]
            [cheshire.core :refer [generate-string]])
  (:import [clojure.lang Sequential]))

(def street-address-url "https://api.smartystreets.com/street-address")
(def zipcode-url "https://api.smartystreets.com/zipcode")

(def street-address-request-fields [:street :street2 :secondary :city :state :zipcode :lastline :addressee :urbanization])
(def zipcode-request-fields [:city :state :zipcode])

(defn- do-request
  "make a call to the smartystreets service"
  [url auth queries]
  (client/post url {:as :json :query-params auth
                    :body (generate-string queries)
                    :headers {"X-Include-Invalid" "true"}}))

(defn- query->response [responses idx query]
  (first (filter #(= idx (:input_index %)) responses)))

(defn- fetch [endpoint fields]
  (fn [auth address-queries]
    (let [responses (->> (map #(select-keys % fields) address-queries)
                         (do-request endpoint auth)
                         :body)]
      (map-indexed (partial query->response responses) address-queries))))

(defmulti norm (fn [fun auth coll] (type coll)))
(defmethod norm Sequential [fun auth coll]
  (fun auth coll))
(defmethod norm :default [fun auth coll]
  (first (fun auth [coll])))

(def street-address
  (partial norm (fetch street-address-url street-address-request-fields)))

(def zipcode
  (partial norm (fetch zipcode-url zipcode-request-fields)))

(def zipcode->city-state
  (partial norm
           (fn [auth zipcodes]
             (->> zipcodes
                  (map (fn [zipcode] {:zipcode zipcode}))
                  (zipcode auth)
                  (map :city_states)
                  (map first)))))
