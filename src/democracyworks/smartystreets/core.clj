(ns democracyworks.smartystreets.core
  (:require
   [clj-http.client :as http]))

(defrecord Client [auth-id auth-token http-params])

(defn client
  "Returns a SmartyStreets client."
  ([auth-id auth-token]
   (client auth-id auth-token {}))
  ([auth-id auth-token http-params]
   {:pre [(string? auth-id)
          (string? auth-token)
          (map? http-params)]}
   (->Client auth-id auth-token http-params)))

(defn http-get
  "Make an HTTP GET call to SmartyStreets."
  [c url query-params]
  (let [opts {:as :json
              :query-params (merge (select-keys c [:auth-id :auth-token])
                                   query-params)}]
    (http/get url (merge opts (:http-params c)))))

(defn http-post
  "Make an HTTP POST call to SmartyStreets."
  [c url body]
  (let [opts {:as :json
              :content-type :json
              :query-params (select-keys c [:auth-id :auth-token])
              :form-params body}]
    (http/post url (merge opts (:http-params c)))))
