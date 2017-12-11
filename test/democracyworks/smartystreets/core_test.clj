(ns democracyworks.smartystreets.core-test
  (:require
   [clj-http.fake :as fake]
   [clojure.test :refer [deftest is testing]]
   [democracyworks.smartystreets.core :as smartystreets]))

(deftest client
  (testing "2-arity version"
    (let [c (smartystreets/client "id" "token")]
      (is (= ["id" "token"]
             ((juxt :auth-id :auth-token) c)))))
  (testing "3-arity version"
    (let [c (smartystreets/client "id" "token" {:foo "bar"})]
      (is (= ["id" "token" {:foo "bar"}]
             ((juxt :auth-id :auth-token :http-params) c)))))
  (testing "bogus arguments not allowed"
    (is (thrown? AssertionError
                 (smartystreets/client nil nil nil)))))

(deftest http-get
  (let [c (smartystreets/client "id" "token")]
    (fake/with-fake-routes {"http://localhost?auth-id=id&auth-token=token&foo=bar"
                            (fn [req]
                              {:status 200
                               :body "{}"})}
      (is (= {} (-> (smartystreets/http-get
                     c
                     "http://localhost"
                     {:foo "bar"})
                    :body))))))

(deftest http-post
  (let [c (smartystreets/client "id" "token")]
    (fake/with-fake-routes {"http://localhost?auth-id=id&auth-token=token"
                            {:post
                             (fn [req]
                               {:status 200
                                :body "[{}]"})}}
      (is (= [{}] (-> (smartystreets/http-post
                       c
                       "http://localhost"
                       {:foo "bar"})
                      :body))))))
