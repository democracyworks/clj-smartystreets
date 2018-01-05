(ns democracyworks.smartystreets.us-zipcode-test
  (:require
   [cheshire.core :as json]
   [clojure.test :refer [deftest is]]
   [clj-http.fake :as fake]
   [democracyworks.smartystreets.core :as smartystreets]
   [democracyworks.smartystreets.us-zipcode :as us-zipcode]))

(def client
  (smartystreets/client "id" "token"))

(deftest parse-response
  (let [data [{:input_index 0 :t "a"}
              {:input_index 2 :t "c"}
              {:input_index 1 :t "b"}]
        result [{:t "a"}
                {:t "b"}
                {:t "c"}]]
    (is (= result (us-zipcode/parse-response data)))))

(deftest fetch-one
  (let [response-data [{:input_index 0 :t "a"}]]
    (fake/with-fake-routes {(str us-zipcode/base-url
                                 "?auth-id=id&auth-token=token")
                            {:get
                             (fn [req]
                               {:status 200
                                :body (json/generate-string response-data)})}}
      (is (= {:t "a"}
             (us-zipcode/fetch-one client {}))))))

(deftest fetch-many
  (let [response-data [{:input_index 0 :t "a"}
                       {:input_index 1 :t "b"}]]
    (fake/with-fake-routes {(str us-zipcode/base-url
                                 "?auth-id=id&auth-token=token")
                            {:post
                             (fn [req]
                               {:status 200
                                :body (json/generate-string response-data)})}}
      (is (= [{:t "a"} {:t "b"}]
             (us-zipcode/fetch-many client [{:street "123 Test St"}
                                            {:street "234 Test St"}]))))))
