(ns democracyworks.smartystreets.core-test
  (:require
   [clj-http.fake :as fake]
   [clojure.test :refer [deftest is]]
   [democracyworks.smartystreets.core :as ss]))

(deftest client
  (let [c (ss/client "id" "token")]
    (is (= ["id" "token"]
           ((juxt :auth-id :auth-token) c)))))

(deftest http-get
  (let [c (ss/client "id" "token")]
    (fake/with-fake-routes {"http://localhost?auth-id=id&auth-token=token&foo=bar"
                            (fn [req]
                              {:status 200
                               :body "{}"})}
      (is (= {} (-> (ss/http-get
                     c
                     "http://localhost"
                     {:foo "bar"})
                    :body))))))

(deftest http-post
  (let [c (ss/client "id" "token")]
    (fake/with-fake-routes {"http://localhost?auth-id=id&auth-token=token"
                            {:post
                             (fn [req]
                               {:status 200
                                :body "[{}]"})}}
      (is (= [{}] (-> (ss/http-post
                       c
                       "http://localhost"
                       {:foo "bar"})
                      :body))))))
