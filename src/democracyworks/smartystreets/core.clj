(ns democracyworks.smartystreets.core
  (:require
   [clj-http.client :as http]))

(defrecord Client [auth-id auth-token query-params http-params])

(defn client
  "Returns a SmartyStreets client.

  * `query-params` is a Keyword->String map of query string parameters to pass
    along with every request issued using this `Client`. This is useful for
    parameters like `license` that need to be supplied in GET and POST requests.
  * `http-params` are additional parameters to pass to the underlying HTTP
    library."
  ([auth-id auth-token]
   (client auth-id auth-token {} {}))
  ([auth-id auth-token http-params]
   (client auth-id auth-token {} http-params))
  ([auth-id auth-token query-params http-params]
   {:pre [(string? auth-id)
          (string? auth-token)
          (map? query-params)
          (map? http-params)]}
   (->Client auth-id auth-token query-params http-params)))

(defn http-get
  "Make an HTTP GET call to SmartyStreets."
  [c url query-params]
  (let [opts {:as :json
              :query-params (merge (select-keys c [:auth-id :auth-token])
                                   (get c :query-params {})
                                   query-params)}]
    (http/get url (merge opts (:http-params c)))))

(defn http-post
  "Make an HTTP POST call to SmartyStreets."
  [c url body]
  (let [opts {:as :json
              :content-type :json
              :query-params (merge (select-keys c [:auth-id :auth-token])
                                   (get c :query-params {}))
              :form-params body}]
    (http/post url (merge opts (:http-params c)))))
