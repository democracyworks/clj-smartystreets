(ns democracyworks.smartystreets.us-zipcode
  "Smarty US Zip Code API

  https://smarty.com/docs/cloud/us-zipcode-api"
  (:require
   [democracyworks.smartystreets.core :as smartystreets])
  (:import
   (democracyworks.smartystreets.core Client)))

(defprotocol API
  (fetch-one [_ request]
    "Retrieve a single US zipcode result from the API.")
  (fetch-many [_ requests]
    "Retrieve a collection of US zipcode results from the API."))

;;;; HTTP implementation

(def base-url
  "https://us-zipcode.api.smarty.com/lookup")

(defn parse-response
  "Transform a sequence `s` of maps containing `:input_index` into a vector with
  entries at offsets corresponding to `:input_index`. The original maps will
  have their `:input_index` key removed."
  [s]
  (mapv #(dissoc % :input_index) (sort-by :input_index s)))

(defn http-fetch-one
  "Uses `client` to send a single zipcode `request` to the Smarty API.

  `request` should be an HTTP input request, described here:

  https://smarty.com/docs/cloud/us-zipcode-api#http-request-input-fields

  Returns the response map API endpoint."
  [client request]
  (-> (smartystreets/http-get client base-url request)
      :body
      (parse-response)
      (first)))

(defn http-fetch-many
  "Uses `client` to send a collection of US zipcode `requests` to the Smarty
  API.

  `requests` should be a sequence of HTTP input requests, described here:

  https://smarty.com/docs/cloud/us-zipcode-api#http-request-input-fields

  Returns a vector of response maps at indexes corresponding to their position
  in `requests`."
  [client requests]
  (-> (smartystreets/http-post client base-url requests)
      :body
      (parse-response)))

(extend Client
  API
  {:fetch-one http-fetch-one
   :fetch-many http-fetch-many})
