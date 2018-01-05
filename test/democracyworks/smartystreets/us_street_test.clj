(ns democracyworks.smartystreets.us-street-test
  (:require
   [cheshire.core :as json]
   [clojure.test :refer [deftest is]]
   [clj-http.fake :as fake]
   [democracyworks.smartystreets.core :as smartystreets]
   [democracyworks.smartystreets.us-street :as us-street]))

(def client
  (smartystreets/client "id" "token"))

(deftest parse-response
  (let [data [{:input_index 0 :candidate_index 0 :t "a"}
              {:input_index 2 :candidate_index 1 :t "c"}
              {:input_index 2 :candidate_index 0 :t "b"}]
        result [[{:t "a"}]
                []
                [{:t "b"}
                 {:t "c"}]]]
    (is (= result (us-street/parse-response 3 data)))))

(deftest http-fetch-one
  (let [response-data [{:input_index 0 :candidate_index 0 :t "a"}
                       {:input_index 0 :candidate_index 1 :t "b"}]]
    (fake/with-fake-routes {(str us-street/base-url
                                 "?auth-id=id&auth-token=token")
                            {:get
                             (fn [req]
                               {:status 200
                                :body (json/generate-string response-data)})}}
      (is (= [{:t "a"} {:t "b"}]
             (us-street/fetch-one client {}))))))

(deftest http-fetch-many
  (let [response-data [{:input_index 0 :candidate_index 0 :t "a"}
                       {:input_index 0 :candidate_index 1 :t "b"}]]
    (fake/with-fake-routes {(str us-street/base-url
                                 "?auth-id=id&auth-token=token")
                            {:post
                             (fn [req]
                               {:status 200
                                :body (json/generate-string response-data)})}}
      (is (= [[{:t "a"} {:t "b"}]]
             (us-street/fetch-many client [{:street "123 Test St"}]))))))
