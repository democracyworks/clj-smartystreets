(ns democracyworks.smartystreets.core
  (:require
   [clj-http.client :as http]))

(defrecord Client [auth-id auth-token])

(defn client
  "Returns a SmartyStreets client."
  [auth-id auth-token]
  (->Client auth-id auth-token))

(defn http-get
  "Make an HTTP GET call to SmartyStreets."
  [c url query-params]
  (http/get url {:as :json
                 :query-params (merge (select-keys c [:auth-id :auth-token])
                                      query-params)}))

(defn http-post
  "Make an HTTP POST call to SmartyStreets."
  [c url body]
  (http/post url {:as :json
                  :content-type :json
                  :query-params (select-keys c [:auth-id :auth-token])
                  :form-params body}))
