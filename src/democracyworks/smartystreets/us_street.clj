(ns democracyworks.smartystreets.us-street
  "Smarty US Street Address API

  https://smarty.com/docs/cloud/us-street-api"
  (:require
   [democracyworks.smartystreets.core :as smartystreets])
  (:import
   (democracyworks.smartystreets.core Client)))

(defprotocol API
  (fetch-one [_ request]
    "Retrieve a single US street address result from the API.")
  (fetch-many [_ requests]
    "Retrieve a collection of US street address results from the API."))

;;;; HTTP implementation

(def base-url
  "https://us-street.api.smarty.com/street-address")

(defn parse-response
  "Transform a sequence `s` of maps containing `:input_index` and
  `:candidate_index` into a vector of length `n` of vectors in order of
  `:input_index` and with the sub-vectors in order of `:candidate_index`. The
  original maps will have their `:input_index` and `:candidate_index` keys
  removed. Top-level-vector offsets without data will contain `nil`."
  [n s]
  (->> (reduce (fn [acc v]
                 (update acc
                         (:input_index v)
                         (fnil assoc (sorted-map))
                         (:candidate_index v)
                         (dissoc v :input_index :candidate_index)))
               (vec (repeat n nil))
               s)
       (mapv (comp vec vals))))

(defn http-fetch-one
  "Uses `client` to send a single address `request` to the Smarty API.

  `request` should be an HTTP input request, described here:

  https://smarty.com/docs/cloud/us-street-api#input-fields

  Returns a vector of candidate addresses, or `nil` if no candidate addresses
  were found."
  [client request]
  (->> (smartystreets/http-get client base-url request)
       :body
       (parse-response 1)
       (first)))

(defn http-fetch-many
  "Uses `client` to send a collection of address `requests` to the Smarty API.

  `requests` should be a collection of HTTP input requests, described here:

  https://smarty.com/docs/cloud/us-street-api#input-fields

  Returns a vector with results at indexes corresponding to their position in
  `requests`. Each entry in the vector will be a vector of candidate addresses
  returned by the API, or `nil` if there was no matching response."
  [client requests]
  (->> (smartystreets/http-post client base-url requests)
       :body
       (parse-response (count requests))))

(extend Client
  API
  {:fetch-one http-fetch-one
   :fetch-many http-fetch-many})
